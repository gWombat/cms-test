package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.core.configurers.CmsCallPostConfigurer;

import java.util.List;

public interface CallConfigurerRegistry {

    CallConfigurerRegistry addCallConfigurer(AbstractCallConfigurer<?> callConfigurer);

    CallConfigurerRegistry addCallConfigurers(List<AbstractCallConfigurer<?>> configurers);

    CallConfigurerRegistry addCallPostConfiguer(CmsCallPostConfigurer postConfigurer);
}
