package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsConfiguration;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.ArrayList;
import java.util.List;

public class CallConfigurationChainImpl implements CallConfigurationChain {

    private final List<AbstractCallConfigurer> configurers;

    public CallConfigurationChainImpl() {
        configurers = new ArrayList<>(0);
    }

    @Override
    public void configure(final CmsConfiguration cmsConfiguration, final List<CmsPath> calls) {
        configurers.stream()
                .filter(c -> c.isExecutable(cmsConfiguration))
                .forEach(c -> c.configure(cmsConfiguration, calls));
    }

    public void addConfigurers(List<AbstractCallConfigurer> configurers) {
        configurers.addAll(configurers);
    }
}
