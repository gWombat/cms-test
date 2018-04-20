package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;

import java.util.ArrayList;
import java.util.List;

public class CallConfigurerRegistryService implements CallConfigurerRegistry {

    private final List<AbstractCallConfigurer<?>> configurers;

    public CallConfigurerRegistryService() {
        configurers = new ArrayList<>(0);
    }

    @Override
    public CallConfigurerRegistry addCallConfigurer(AbstractCallConfigurer<?> callConfigurer) {
        configurers.add(callConfigurer);

        return this;
    }

    public List<AbstractCallConfigurer<?>> getConfigurers() {
        return configurers;
    }

    @Override
    public CallConfigurerRegistry addCallConfigurers(List<AbstractCallConfigurer<?>> configurers) {
        if(configurers != null)
            this.configurers.addAll(configurers);
        return this;
    }
}
