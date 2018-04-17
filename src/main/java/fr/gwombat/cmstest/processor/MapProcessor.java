package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.utils.CmsProcessorUtils;
import fr.gwombat.cmstest.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Service
public class MapProcessor implements CmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapProcessor.class);

    private final CmsResultProcessingChain cmsResultProcessingChain;

    public MapProcessor(CmsResultProcessingChain cmsResultProcessingChain) {
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isMap(clazz);
    }

    @Override
    public Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName) {
        LOGGER.debug("Invoking map processing on root node name: {}", rootName);
        final Map<String, Map<String, String>> mapItems = CmsProcessorUtils.getGroupedCmsResultsSubMap(cmsResults, rootName);
        final Map<String, Object> map = new HashMap<>(mapItems.size());

        final Class<?> valueClass = (Class<?>) parameterizedType.getActualTypeArguments()[1];

        LOGGER.debug("Processing map of type: {}<{},{}>", clazz, String.class, valueClass);
        for (Map.Entry<String, Map<String, String>> entry : mapItems.entrySet()) {
            final String propertyKey = rootName + "/" + entry.getKey();
            final Object mapItem = cmsResultProcessingChain.process(valueClass, cmsResults, null, propertyKey);
            LOGGER.debug("map item built: {}", mapItem);
            map.put(entry.getKey(), mapItem);
        }

        return map;
    }
}