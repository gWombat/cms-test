package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallWrapper;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.ArrayList;
import java.util.List;

public class CallConfigurationChainImpl implements CallConfigurationChain {

    private final List<AbstractCallConfigurer> configurers;

    public CallConfigurationChainImpl() {
        configurers = new ArrayList<>(0);
    }

    @Override
    public void configure(final CmsCallWrapper cmsCallWrapper, final List<CmsPath> calls, final LocalContext context) {
        configurers.stream()
                .filter(c -> c.isExecutable(cmsCallWrapper))
                .forEach(c -> c.configure(cmsCallWrapper, calls, context));
    }

    public void addConfigurers(List<AbstractCallConfigurer<?>> configurers) {
        this.configurers.addAll(configurers);
    }
}
