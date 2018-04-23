package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.mapping.manager.AbstractCmsManager;

public class JackrabbitManagerImpl extends AbstractCmsManager<AbstractJackrabbitConfigurer> {

    @Override
    protected ConfigurationContext createConfigurationContext(AbstractJackrabbitConfigurer configurer, Long departureCityId) {
        final JackrabbitConfigurationContext localContext = new JackrabbitConfigurationContext();
        localContext.setDepartureCityId(departureCityId);
        localContext.setLanguage(configurer.getLanguage());
        localContext.setBrandNode(configurer.getBrandNode());
        localContext.setBrandNodeSpecific(configurer.getBrandNodeSpecific());

        return localContext;
    }
}
