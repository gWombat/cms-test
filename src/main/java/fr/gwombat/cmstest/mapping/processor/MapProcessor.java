package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MapProcessor extends AbstractChainableCmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapProcessor.class);

    public MapProcessor(CmsConfigurer cmsConfigurer, CmsResultProcessingChain cmsResultProcessingChain) {
        super(cmsConfigurer, cmsResultProcessingChain);
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isMap(clazz);
    }

    @Override
    public Object process(final Map<String, String> cmsResults, final ResultProcessingContext context) throws CmsMappingException {
        LOGGER.debug("Invoking map processing on root node name: {}", context.getPath());
        final Map<String, Map<String, String>> mapItems = getGroupedCmsResultsSubMap(cmsResults, context.getPath());
        final Map<String, Object> map = new HashMap<>(mapItems.size());

        final Class<?> valueClass = (Class<?>) context.getParameterizedType().getActualTypeArguments()[1];

        LOGGER.debug("Processing map of type: {}<{},{}>", context.getObjectType(), String.class, valueClass);
        for (Map.Entry<String, Map<String, String>> entry : mapItems.entrySet()) {
            final String propertyPath = getPropertyPath(context.getPath(), entry.getKey());
            final ResultProcessingContext newContext = new ResultProcessingContext();
            newContext.setObjectType(valueClass);
            newContext.setPath(propertyPath);

            final Object mapItem = cmsResultProcessingChain.process(cmsResults, newContext);
            LOGGER.debug("map item built: {}", mapItem);
            map.put(entry.getKey(), mapItem);
        }

        return map;
    }
}
