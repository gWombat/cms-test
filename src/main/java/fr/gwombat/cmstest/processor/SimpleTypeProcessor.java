package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.utils.CmsProcessorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public class SimpleTypeProcessor implements CmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTypeProcessor.class);

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return CmsProcessorUtils.isSimpleType(clazz);
    }

    @Override
    public Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName) {
        LOGGER.debug("Processing property {}, of type {}", rootName, clazz);
        LOGGER.debug("available results: {}", cmsResults);
        final String value = cmsResults.get(rootName);
        LOGGER.debug("Corresponding value is: {}", value);
        return CmsProcessorUtils.castValue(clazz, value);
    }
}
