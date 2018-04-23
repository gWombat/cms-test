package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;

import java.util.ArrayList;
import java.util.List;

public class CallConfigurationChainImpl implements CallConfigurationChain {

    private final List<AbstractCallConfigurer> configurers;

    public CallConfigurationChainImpl() {
        configurers = new ArrayList<>(0);
    }

    @Override
    public void configure(final CmsCallConfigWrapper cmsCallConfigWrapper, final List<CmsPath> calls, final ConfigurationContext context) throws CmsConfigurationException {

        for (AbstractCallConfigurer<CmsCallConfigWrapper> configurer : configurers)
            if (configurer.isExecutable(cmsCallConfigWrapper))
                configurer.configure(cmsCallConfigWrapper, calls, context);
    }

    public void addConfigurers(List<AbstractCallConfigurer<?>> configurers) {
        this.configurers.addAll(configurers);
    }
}
