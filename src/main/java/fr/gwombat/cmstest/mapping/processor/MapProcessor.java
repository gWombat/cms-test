package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.context.CmsContextFacade;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class MapProcessor extends AbstractCmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapProcessor.class);

    public MapProcessor(CmsContextFacade cmsContextFacade) {
        super(cmsContextFacade);
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isMap(clazz);
    }

    @Override
    public Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName) throws CmsMappingException {
        LOGGER.debug("Invoking map processing on root node name: {}", rootName);
        final Map<String, Map<String, String>> mapItems = getGroupedCmsResultsSubMap(cmsResults, rootName);
        final Map<String, Object> map = new HashMap<>(mapItems.size());

        final Class<?> valueClass = (Class<?>) parameterizedType.getActualTypeArguments()[1];

        LOGGER.debug("Processing map of type: {}<{},{}>", clazz, String.class, valueClass);
        for (Map.Entry<String, Map<String, String>> entry : mapItems.entrySet()) {
            final String propertyPath = cmsContextFacade.getPropertyPath(rootName, entry.getKey());
            final Object mapItem = cmsContextFacade.getProcessingChain().process(valueClass, cmsResults, null, propertyPath);
            LOGGER.debug("map item built: {}", mapItem);
            map.put(entry.getKey(), mapItem);
        }

        return map;
    }
}
