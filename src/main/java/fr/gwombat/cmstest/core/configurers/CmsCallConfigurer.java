package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;

import java.util.List;

public interface CmsCallConfigurer {

    boolean isExecutable(CmsCallConfigWrapper cmsCallWrapper);

    void configure(CmsCallConfigWrapper cmsCallWrapper, List<CmsPath> calls, ConfigurationContext context) throws CmsConfigurationException;

}
