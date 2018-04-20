package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.configuration.CmsConfigurer;

public abstract class AbstractChainableCmsProcessor extends AbstractCmsProcessor {

    protected final CmsResultProcessingChain cmsResultProcessingChain;

    public AbstractChainableCmsProcessor(CmsConfigurer cmsConfigurer, CmsResultProcessingChain cmsResultProcessingChain) {
        super(cmsConfigurer);
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    protected final String getPropertyPath(final String rootPath, final String propertyName) {
        return rootPath + cmsConfigurer.getPropertySeparator() + propertyName;
    }
}
