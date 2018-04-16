package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.utils.CmsProcessorUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public class CollectionProcessor implements CmsProcessor {

    private final CmsResultProcessingChain cmsResultProcessingChain;

    public CollectionProcessor(CmsResultProcessingChain cmsResultProcessingChain) {
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return CmsProcessorUtils.isCollection(clazz);
    }

    @Override
    public Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName) {
        final Map<String, Map<String, String>> collectionElements = CmsProcessorUtils.getGroupedCmsResultsSubMap(cmsResults, rootName);
        final List<Object> collection = new ArrayList<>(collectionElements.size());
        for (Map.Entry<String, Map<String, String>> entry : collectionElements.entrySet()) {
            try {
                final Class<?> parameterizedTypeClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                Object listItem = parameterizedTypeClass.newInstance();
                listItem = cmsResultProcessingChain.process(listItem.getClass(), entry.getValue(), null, entry.getKey());
                collection.add(listItem);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return collection;
    }
}
