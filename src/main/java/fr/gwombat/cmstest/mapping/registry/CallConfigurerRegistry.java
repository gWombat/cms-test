package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;

public interface CallConfigurerRegistry {

    CallConfigurerRegistry addCallConfigurer(AbstractCallConfigurer callConfigurer);
}
