package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.context.CmsContextFacade;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public class CollectionProcessor extends AbstractCmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionProcessor.class);

    public CollectionProcessor(final CmsContextFacade cmsContextFacade) {
        super(cmsContextFacade);
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isCollection(clazz);
    }

    @Override
    public Object process(final Map<String, String> cmsResults, final Class<?> clazz, final ParameterizedType parameterizedType, final String rootName) throws CmsMappingException {
        LOGGER.debug("Invoking collection processing on root node name: {}", rootName);
        if(parameterizedType == null)
            throw new IllegalArgumentException("The type of collection must be set");

        final Map<String, Map<String, String>> collectionItems = getGroupedCmsResultsSubMap(cmsResults, rootName);
        final List<Object> collection = new ArrayList<>(collectionItems.size());
        final Class<?> parameterizedTypeClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];

        LOGGER.debug("Processing collection of type: {}<{}>", clazz, parameterizedTypeClass);
        for (Map.Entry<String, Map<String, String>> entry : collectionItems.entrySet()) {
            final String propertyPath = cmsContextFacade.getPropertyPath(rootName, entry.getKey());
            final Object listItem = cmsContextFacade.getProcessingChain().process(parameterizedTypeClass, cmsResults, null, propertyPath);
            LOGGER.debug("collection item built: {}", listItem);
            collection.add(listItem);
        }
        return collection;
    }
}
