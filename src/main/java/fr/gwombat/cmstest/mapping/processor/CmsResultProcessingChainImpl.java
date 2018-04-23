package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.context.ResultProcessingContext;

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
    public Object process(final Map<String, String> cmsResults, final ResultProcessingContext context) throws CmsMappingException {
        final CmsProcessor matchingProcessor = processors
                .stream()
                .filter(p -> p.isExecutable(context.getObjectType()))
                .findFirst()
                .orElse((r, c) -> {
                    throw new CmsMappingException("No processor found for type: " + c.getObjectType());
                });

        return matchingProcessor.process(cmsResults, context);
    }

    public void addProcessor(CmsProcessor cmsProcessor) {
        this.processors.add(cmsProcessor);
    }

}
