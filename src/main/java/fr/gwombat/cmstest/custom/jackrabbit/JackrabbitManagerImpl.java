package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.context.DynamicContext;
import fr.gwombat.cmstest.mapping.manager.AbstractCmsManager;

public class JackrabbitManagerImpl extends AbstractCmsManager<AbstractJackrabbitConfigurer> {

    @Override
    protected ConfigurationContext createConfigurationContext(AbstractJackrabbitConfigurer configurer, final DynamicContext dynamicContext) {
        final JackrabbitConfigurationContext localContext = new JackrabbitConfigurationContext();
        localContext.setDynamicContext(dynamicContext);
        localContext.setLanguage(configurer.getLanguage());
        localContext.setBrandNode(configurer.getBrandNode());
        localContext.setBrandNodeSpecific(configurer.getBrandNodeSpecific());

        return localContext;
    }
}
