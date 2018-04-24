package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.configuration.CmsConfigurer;

public abstract class AbstractChainableCmsProcessor extends AbstractCmsProcessor {

    protected final CmsResultProcessingChain cmsResultProcessingChain;

    public AbstractChainableCmsProcessor(CmsConfigurer cmsConfigurer, CmsResultProcessingChain cmsResultProcessingChain) {
        super(cmsConfigurer);
        this.cmsResultProcessingChain = cmsResultProcessingChain;
    }

    protected final String getPropertyPath(final String rootPath, final String propertyName) {
        final StringBuilder stringBuilder = new StringBuilder(rootPath);

        if (!rootPath.endsWith(cmsConfigurer.getPropertySeparator()) && propertyName != null && !"".equals(propertyName))
            stringBuilder.append(cmsConfigurer.getPropertySeparator());
        stringBuilder.append(propertyName);

        return stringBuilder.toString();
    }
}
