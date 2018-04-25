package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;

import java.util.ArrayList;
import java.util.List;

public class CallConfigurationChainImpl implements CallConfigurationChain {

    private final List<CmsCallConfigurer>     configurers;
    private final List<CmsCallPostConfigurer> postConfigurers;

    public CallConfigurationChainImpl() {
        configurers = new ArrayList<>(0);
        postConfigurers = new ArrayList<>(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(final CmsCallConfigWrapper cmsCallConfigWrapper, final List<CmsPath> calls, final ConfigurationContext context) throws CmsConfigurationException {

        for (CmsCallConfigurer configurer : configurers)
            if (configurer.isExecutable(cmsCallConfigWrapper))
                configurer.configure(cmsCallConfigWrapper, calls, context);

        postConfigurers.forEach(postConfigurer -> postConfigurer.postConfigure(calls));
    }

    public void addConfigurers(List<CmsCallConfigurer> configurers) {
        this.configurers.addAll(configurers);
    }

    public void addPostConfigurers(List<CmsCallPostConfigurer> postConfigurers) {
        this.postConfigurers.addAll(postConfigurers);
    }
}
