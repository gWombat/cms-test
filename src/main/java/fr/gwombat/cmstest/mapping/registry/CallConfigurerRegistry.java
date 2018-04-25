package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.core.configurers.CmsCallConfigurer;
import fr.gwombat.cmstest.core.configurers.CmsCallPostConfigurer;

import java.util.List;

public interface CallConfigurerRegistry {

    CallConfigurerRegistry addCallConfigurer(CmsCallConfigurer callConfigurer);

    CallConfigurerRegistry addCallConfigurers(List<CmsCallConfigurer> configurers);

    CallConfigurerRegistry addCallPostConfiguer(CmsCallPostConfigurer postConfigurer);
}
