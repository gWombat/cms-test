package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

public class EnumProcessor implements CmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumProcessor.class);

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isEnum(clazz);
    }

    @Override
    public Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName) {
        LOGGER.debug("Processing property {}, of type {}", rootName, clazz);
        final String value = cmsResults.get(rootName);
        LOGGER.debug("Value found: {}", value);
        return Enum.valueOf((Class<Enum>) clazz, value);
    }
}
