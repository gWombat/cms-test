package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.annotations.CmsElement;
import fr.gwombat.cmstest.converters.Converter;
import fr.gwombat.cmstest.converters.DefaultConverter;
import fr.gwombat.cmstest.converters.PostConverter;
import fr.gwombat.cmstest.utils.CmsProcessorUtils;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
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
        return !CmsProcessorUtils.isCollection(clazz)
                && !CmsProcessorUtils.isSimpleType(clazz)
                && !CmsProcessorUtils.isMap(clazz);
    }

    @Override
    public Object process(final Map<String, String> cmsResults, final Class<?> clazz, ParameterizedType parameterizedType, final String rootName) {
        Object target = null;
        try {
            final CmsElement cmsElementAnnotation = clazz.getAnnotation(CmsElement.class);
            if (cmsElementAnnotation.converter() != DefaultConverter.class) {
                final Class<? extends Converter> converterClass = cmsElementAnnotation.converter();
                final Converter converter = converterClass.newInstance();
                target = converter.convert(cmsResults);
                applyPostConverters(target);
                return target;
            }

            target = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                logger.debug("Working on field {}", field);
                String propertyKey = CmsProcessorUtils.detectPropertyName(field);
                Method matchMethod = null;
                final String setterName = getSetterName(field.getName());
                logger.debug("Setter name: {}", setterName);

                for (Method method : clazz.getMethods()) {
                    if (method.getName().equals(setterName)) {
                        matchMethod = method;
                        break;
                    }
                }

                if (matchMethod == null)
                    continue;

                logger.debug("Matching method found! {}", matchMethod);
                if (matchMethod.getParameterCount() == 1) {
                    final String candidatePropertyName = CmsProcessorUtils.detectPropertyName(matchMethod);
                    if (candidatePropertyName != null)
                        propertyKey = candidatePropertyName;

                    final Class<?> parameterType = matchMethod.getParameterTypes()[0];

                    ParameterizedType parameterizedType1 = null;
                    if (field.getGenericType() instanceof ParameterizedType)
                        parameterizedType1 = (ParameterizedType) field.getGenericType();

                    final Object paramValue = cmsResultProcessingChain.process(parameterType, cmsResults, parameterizedType1, propertyKey);

                    logger.debug("Invoking setter...");
                    matchMethod.invoke(target, paramValue);
                    logger.debug("Setter invoked successfully!");

                }
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return target;
    }

    private static void applyPostConverters(Object target) throws IllegalAccessException, InstantiationException {
        final CmsElement annotation = target.getClass().getAnnotation(CmsElement.class);
        if (annotation != null) {
            for (Class<? extends PostConverter> postConverterClass : annotation.postConverters()) {
                final PostConverter postConverter = postConverterClass.newInstance();
                postConverter.postConvert(target);
            }
        }
    }

    private static String getSetterName(final String propertyName) {
        return "set" + WordUtils.capitalize(propertyName);
    }

}
