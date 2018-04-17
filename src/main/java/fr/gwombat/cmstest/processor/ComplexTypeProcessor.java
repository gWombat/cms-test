package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.annotations.CmsElement;
import fr.gwombat.cmstest.converters.Converter;
import fr.gwombat.cmstest.converters.DefaultConverter;
import fr.gwombat.cmstest.converters.PostConverter;
import fr.gwombat.cmstest.utils.AnnotationDetectorUtils;
import fr.gwombat.cmstest.utils.CmsProcessorUtils;
import fr.gwombat.cmstest.utils.TypeUtils;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
@Service
public class ComplexTypeProcessor implements CmsProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ComplexTypeProcessor.class);

    private final CmsResultProcessingChain cmsResultProcessingChain;

    public ComplexTypeProcessor(CmsResultProcessingChain cmsResultProcessingChain) {
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return !TypeUtils.isCollection(clazz)
                && !TypeUtils.isSimpleType(clazz)
                && !TypeUtils.isMap(clazz);
    }

    @Override
    public Object process(final Map<String, String> cmsResults, final Class<?> clazz, ParameterizedType parameterizedType, final String rootName) {
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

                    propertyKey = rootName + "/" + propertyKey;
                    final Object paramValue = cmsResultProcessingChain.process(parameterType, cmsResults, fieldParameterizedType, propertyKey);

                    logger.debug("Invoking setter {}", matchMethod);
                    matchMethod.invoke(target, paramValue);
                    logger.debug("Setter {} invoked successfully!", matchMethod);
                }
            }
            applyPostConverters(target);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return target;
    }

    private static Method findMatchingMethod(final List<Method> methods, final String setterName) {
        for (Method method : methods) {
            if (method.getName().equals(setterName))
                return method;
        }
        return null;
    }

    private static boolean isCustomConverterAvailable(final Class<?> clazz) {
        final CmsElement cmsElementAnnotation = clazz.getAnnotation(CmsElement.class);
        return cmsElementAnnotation != null && cmsElementAnnotation.converter() != DefaultConverter.class;
    }

    private static Object applyCustomConverter(final Class<?> clazz, final Map<String, String> cmsResults, final String rootName) {
        Object target = null;
        final CmsElement cmsElementAnnotation = clazz.getAnnotation(CmsElement.class);
        try {
            final Class<? extends Converter> converterClass = cmsElementAnnotation.converter();
            final Converter converter = converterClass.newInstance();
            final Map<String, String> test = CmsProcessorUtils.getCmsResultsSubMap(cmsResults, rootName);
            logger.debug("Invoking Converter {}...", converter);
            target = converter.convert(test);
            applyPostConverters(target);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return target;
    }

    private static void applyPostConverters(Object target) throws IllegalAccessException, InstantiationException {
        final CmsElement annotation = target.getClass().getAnnotation(CmsElement.class);
        if (annotation != null) {
            for (Class<? extends PostConverter> postConverterClass : annotation.postConverters()) {
                final PostConverter postConverter = postConverterClass.newInstance();
                logger.debug("Invoking post-converter {}...", postConverter);
                postConverter.postConvert(target);
            }
        }
    }

    private static String getSetterName(final String propertyName) {
        return "set" + WordUtils.capitalize(propertyName);
    }

}
