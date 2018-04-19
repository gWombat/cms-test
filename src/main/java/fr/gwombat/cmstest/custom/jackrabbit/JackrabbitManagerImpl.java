package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.mapping.manager.AbstractCmsManager;
import org.springframework.stereotype.Service;

public class JackrabbitManagerImpl extends AbstractCmsManager<AbstractJackrabbitConfigurer> {

    @Override
    protected LocalContext createLocalContext(AbstractJackrabbitConfigurer configurer, Long departureCityId) {
        final JackrabbitLocalContext localContext = new JackrabbitLocalContext();
        localContext.setDepartureCityId(departureCityId);
        localContext.setLanguage(configurer.getLanguage());
        localContext.setBrandNode(configurer.getBrandNode());
        localContext.setBrandNodeSpecific(configurer.getBrandNodeSpecific());

        return localContext;
    }
}
