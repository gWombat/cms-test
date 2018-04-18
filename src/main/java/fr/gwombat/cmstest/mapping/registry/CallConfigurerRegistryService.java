package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;

import java.util.List;

public class CallConfigurerRegistryService implements CallConfigurerRegistry {

    private List<AbstractCallConfigurer> configurers;

    @Override
    public CallConfigurerRegistry addCallConfigurer(AbstractCallConfigurer callConfigurer) {
        configurers.add(callConfigurer);

        return this;
    }

    public List<AbstractCallConfigurer> getConfigurers() {
        return configurers;
    }
}
