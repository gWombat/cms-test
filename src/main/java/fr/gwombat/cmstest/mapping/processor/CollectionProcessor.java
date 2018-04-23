package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public class CollectionProcessor extends AbstractChainableCmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionProcessor.class);

    public CollectionProcessor(CmsConfigurer cmsConfigurer, CmsResultProcessingChain cmsResultProcessingChain) {
        super(cmsConfigurer, cmsResultProcessingChain);
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isCollection(clazz);
    }

    @Override
    public Object process(final Map<String, String> cmsResults, final ResultProcessingContext context) throws CmsMappingException {
        LOGGER.debug("Invoking collection processing on root node name: {}", context.getPath());
        if (context.getParameterizedType() == null)
            throw new IllegalArgumentException("The type of collection must be set");

        final Map<String, Map<String, String>> collectionItems = getGroupedCmsResultsSubMap(cmsResults, context.getPath());
        final List<Object> collection = new ArrayList<>(collectionItems.size());
        final Class<?> parameterizedTypeClass = (Class<?>) context.getParameterizedType().getActualTypeArguments()[0];

        LOGGER.debug("Processing collection of type: {}<{}>", context.getObjectType(), parameterizedTypeClass);
        for (Map.Entry<String, Map<String, String>> entry : collectionItems.entrySet()) {
            final String propertyPath = getPropertyPath(context.getPath(), entry.getKey());
            final ResultProcessingContext newContext = new ResultProcessingContext();
            newContext.setPath(propertyPath);
            newContext.setObjectType(parameterizedTypeClass);
            newContext.setDynamicNodesContext(context.getDynamicNodesContext());

            final Object listItem = cmsResultProcessingChain.process(cmsResults, newContext);
            LOGGER.debug("collection item built: {}", listItem);
            collection.add(listItem);
        }
        return collection;
    }
}
