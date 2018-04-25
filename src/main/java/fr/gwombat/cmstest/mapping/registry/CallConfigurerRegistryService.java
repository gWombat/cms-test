package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.core.configurers.CmsCallPostConfigurer;

import java.util.ArrayList;
import java.util.List;

public class CallConfigurerRegistryService implements CallConfigurerRegistry {

    private final List<AbstractCallConfigurer<?>> configurers;
    private final List<CmsCallPostConfigurer>     postConfigurers;

    public CallConfigurerRegistryService() {
        configurers = new ArrayList<>(0);
        postConfigurers = new ArrayList<>(0);
    }

    @Override
    public CallConfigurerRegistry addCallConfigurer(AbstractCallConfigurer<?> callConfigurer) {
        configurers.add(callConfigurer);

        return this;
    }

    @Override
    public CallConfigurerRegistry addCallConfigurers(List<AbstractCallConfigurer<?>> configurers) {
        if (configurers != null)
            this.configurers.addAll(configurers);
        return this;
    }

    @Override
    public CallConfigurerRegistry addCallPostConfiguer(CmsCallPostConfigurer postConfigurer) {
        if (postConfigurer != null)
            this.postConfigurers.add(postConfigurer);
        return this;
    }

    public List<AbstractCallConfigurer<?>> getConfigurers() {
        return configurers;
    }

    public List<CmsCallPostConfigurer> getPostConfigurers() {
        return postConfigurers;
    }
}
