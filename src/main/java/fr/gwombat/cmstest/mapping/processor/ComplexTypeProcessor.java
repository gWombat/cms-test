package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.annotations.CmsElement;
import fr.gwombat.cmstest.mapping.context.ResultProcessingContext;
import fr.gwombat.cmstest.mapping.converters.Converter;
import fr.gwombat.cmstest.mapping.converters.DefaultConverter;
import fr.gwombat.cmstest.mapping.converters.PostConverter;
import fr.gwombat.cmstest.mapping.invoker.SetterInvoker;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistryService;
import fr.gwombat.cmstest.mapping.utils.AnnotationDetectorUtils;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import fr.gwombat.cmstest.mapping.utils.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public class ComplexTypeProcessor extends AbstractChainableCmsProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ComplexTypeProcessor.class);

    private final ConverterRegistryService converterRegistryService;

    public ComplexTypeProcessor(CmsConfigurer cmsConfigurer, CmsResultProcessingChain cmsResultProcessingChain, ConverterRegistryService converterRegistryService) {
        super(cmsConfigurer, cmsResultProcessingChain);
        this.converterRegistryService = converterRegistryService;
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isComplexType(clazz);
    }

    @Override
    public Object process(final Map<String, String> cmsResults, final ResultProcessingContext context) throws CmsMappingException {
        Object target;

        final String targetNodeName = AnnotationDetectorUtils.detectRootNodeName(context.getObjectType(), context.getDynamicContext());
        logger.debug("Local context: {}", context);

        if (isCustomConverterAvailable(context.getObjectType()))
            return applyCustomConverter(context.getObjectType(), cmsResults, context.getPath());

        try {
            target = context.getObjectType().newInstance();

            final List<Field> fields = TypeUtils.getAllFields(context.getObjectType());
            final List<Method> methods = TypeUtils.getAllMethods(context.getObjectType());

            for (Field field : fields) {
                logger.debug("Working on field {}", field);
                field.setAccessible(true); // for performance!
                String propertyKey = AnnotationDetectorUtils.detectPropertyName(field);
                final String setterName = getSetterName(field.getName());
                logger.trace("Setter name: {}", setterName);

                final Method matchMethod = findMatchingMethod(methods, setterName);
                if (matchMethod == null) {
                    logger.warn("No matching method found for field: {}. Please add correct setter!", field);
                    continue;
                }

                logger.debug("Matching method found! {}", matchMethod);
                matchMethod.setAccessible(true);
                if (matchMethod.getParameterCount() == 1) {
                    final String candidatePropertyName = AnnotationDetectorUtils.detectPropertyName(matchMethod);
                    if (candidatePropertyName != null)
                        propertyKey = candidatePropertyName;

                    final Class<?> parameterType = matchMethod.getParameterTypes()[0];

                    ParameterizedType fieldParameterizedType = null;
                    if (field.getGenericType() instanceof ParameterizedType)
                        fieldParameterizedType = (ParameterizedType) field.getGenericType();

                    String propertyPath = getPropertyPath(context.getPath(), targetNodeName);
                    if (!TypeUtils.isComplexType(parameterType))
                        propertyPath = getPropertyPath(propertyPath, propertyKey);

                    DateTimeFormatter dateTimeFormatter = AnnotationDetectorUtils.getDateTimeFormatter(field);
                    if (dateTimeFormatter == null)
                        dateTimeFormatter = AnnotationDetectorUtils.getDateTimeFormatter(matchMethod);

                    final ResultProcessingContext newContext = new ResultProcessingContext();
                    newContext.setObjectType(parameterType);
                    newContext.setParameterizedType(fieldParameterizedType);
                    newContext.setPath(propertyPath);
                    newContext.setDateTimeFormatter(dateTimeFormatter);
                    newContext.setDynamicContext(context.getDynamicContext());

                    final Object paramValue = cmsResultProcessingChain.process(cmsResults, newContext);

                    logger.debug("Invoking setter {}", matchMethod);
                    //matchMethod.invoke(target, paramValue);
                    SetterInvoker.invokeSetter(target, matchMethod, paramValue); // invocation via Lambdametafactory
                    logger.debug("Setter {} invoked successfully!", matchMethod);
                }
            }
            applyPostConverters(target);
        } catch (IllegalAccessException /*| InvocationTargetException */ | InstantiationException e) {
            throw new CmsMappingException(e);
        }
        return target;
    }

    private static Method findMatchingMethod(final List<Method> methods, final String setterName) {
        return methods.stream()
                .filter(method -> method.getName().equals(setterName))
                .findFirst()
                .orElse(null);
    }

    private boolean isCustomConverterAvailable(final Class<?> clazz) {
        final CmsElement cmsElementAnnotation = clazz.getAnnotation(CmsElement.class);
        if (cmsElementAnnotation != null)
            return cmsElementAnnotation.converter() != DefaultConverter.class;

        final Converter converter = converterRegistryService.getConverter(clazz);
        return converter != null;
    }

    private Object applyCustomConverter(final Class<?> clazz, final Map<String, String> cmsResults, final String rootName) throws CmsMappingException {
        Object target = null;
        Converter converter;

        final CmsElement cmsElementAnnotation = clazz.getAnnotation(CmsElement.class);

        if (cmsElementAnnotation != null) {
            final Class<? extends Converter> converterClass = cmsElementAnnotation.converter();
            converter = converterRegistryService.getConverter(converterClass);
            if (converter == null)
                throw new CmsMappingException("Enable to find a converter of class " + converterClass + ". Please register one");
        } else
            converter = converterRegistryService.getConverter(clazz);

        if (converter != null) {
            final Map<String, String> test = getCmsResultsSubMap(cmsResults, rootName);
            logger.debug("Invoking Converter {}...", converter);
            target = converter.convert(test);
            applyPostConverters(target);
        }

        return target;
    }

    private void applyPostConverters(Object target) throws CmsMappingException {
        final CmsElement annotation = target.getClass().getAnnotation(CmsElement.class);
        if (annotation != null) {
            for (Class<? extends PostConverter> postConverterClass : annotation.postConverters()) {
                final PostConverter postConverter = converterRegistryService.getPostConverter(postConverterClass);
                if (postConverter == null)
                    throw new CmsMappingException("Enable to find a converter of class " + postConverterClass + ". Please register one");

                logger.debug("Invoking post-converter {}...", postConverter);
                postConverter.postConvert(target);
            }
        }
    }

    private static String getSetterName(final String propertyName) {
        return "set" + WordUtils.capitalize(propertyName);
    }

}
