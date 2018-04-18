package fr.gwombat.cmstest.processor;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public class CmsResultProcessingChainImpl implements CmsResultProcessingChain {

    private final List<CmsProcessor> processors;

    public CmsResultProcessingChainImpl() {
        processors = new ArrayList<>(0);
    }

    @Override
    public Object process(final Class<?> clazz, Map<String, String> cmsResults, ParameterizedType parameterizedType, final String rootName) {
        final CmsProcessor matchingProcessor = processors
                .stream()
                .filter(p -> p.isExecutable(clazz))
                .findFirst()
                .orElse((r, c, p, n) -> null); // should throw exception...

        return matchingProcessor.process(cmsResults, clazz, parameterizedType, rootName);
    }

    public void addProcessor(CmsProcessor cmsProcessor) {
        this.processors.add(cmsProcessor);
    }

}
