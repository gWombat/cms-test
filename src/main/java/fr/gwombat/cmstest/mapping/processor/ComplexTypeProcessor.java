package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.annotations.CmsElement;
import fr.gwombat.cmstest.mapping.converters.Converter;
import fr.gwombat.cmstest.mapping.converters.DefaultConverter;
import fr.gwombat.cmstest.mapping.converters.PostConverter;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistryService;
import fr.gwombat.cmstest.mapping.utils.AnnotationDetectorUtils;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
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
    public Object process(final Map<String, String> cmsResults, final Class<?> clazz, ParameterizedType parameterizedType, final String rootName) throws CmsMappingException {
        Object target = null;

        if (isCustomConverterAvailable(clazz))
            return applyCustomConverter(clazz, cmsResults, rootName);

        try {
            target = clazz.newInstance();

            final List<Field> fields = TypeUtils.getAllFields(clazz);
            final List<Method> methods = TypeUtils.getAllMethods(clazz);

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
                if (matchMethod.getParameterCount() == 1) {
                    final String candidatePropertyName = AnnotationDetectorUtils.detectPropertyName(matchMethod);
                    if (candidatePropertyName != null)
                        propertyKey = candidatePropertyName;

                    final Class<?> parameterType = matchMethod.getParameterTypes()[0];

                    ParameterizedType fieldParameterizedType = null;
                    if (field.getGenericType() instanceof ParameterizedType)
                        fieldParameterizedType = (ParameterizedType) field.getGenericType();

                    final String propertyPath = getPropertyPath(rootName, propertyKey);
                    final Object paramValue = cmsResultProcessingChain.process(parameterType, cmsResults, fieldParameterizedType, propertyPath);

                    logger.debug("Invoking setter {}", matchMethod);
                    matchMethod.invoke(target, paramValue);
                    logger.debug("Setter {} invoked successfully!", matchMethod);
                }
            }
            applyPostConverters(target);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
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

    private static boolean isCustomConverterAvailable(final Class<?> clazz) {
        final CmsElement cmsElementAnnotation = clazz.getAnnotation(CmsElement.class);
        return cmsElementAnnotation != null && cmsElementAnnotation.converter() != DefaultConverter.class;
    }

    private Object applyCustomConverter(final Class<?> clazz, final Map<String, String> cmsResults, final String rootName) throws CmsMappingException {
        Object target = null;
        final CmsElement cmsElementAnnotation = clazz.getAnnotation(CmsElement.class);
        try {
            final Class<? extends Converter> converterClass = cmsElementAnnotation.converter();
            final Converter converter = converterRegistryService.getConverter(converterClass);
            if (converter == null)
                throw new CmsMappingException("Enable to find a converter of class " + converterClass);

            final Map<String, String> test = getCmsResultsSubMap(cmsResults, rootName);
            logger.debug("Invoking Converter {}...", converter);
            target = converter.convert(test);
            applyPostConverters(target);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new CmsMappingException(e);
        }
        return target;
    }

    private void applyPostConverters(Object target) throws IllegalAccessException, InstantiationException {
        final CmsElement annotation = target.getClass().getAnnotation(CmsElement.class);
        if (annotation != null) {
            for (Class<? extends PostConverter> postConverterClass : annotation.postConverters()) {
                final PostConverter postConverter = converterRegistryService.getPostConverter(postConverterClass);
                logger.debug("Invoking post-converter {}...", postConverter);
                postConverter.postConvert(target);
            }
        }
    }

    private static String getSetterName(final String propertyName) {
        return "set" + WordUtils.capitalize(propertyName);
    }

}
