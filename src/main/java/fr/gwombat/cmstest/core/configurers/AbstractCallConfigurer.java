package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsConfiguration;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

public abstract class AbstractCallConfigurer {

    protected abstract boolean isExecutable(CmsConfiguration cmsConfiguration);

    protected abstract void configure(CmsConfiguration cmsConfiguration, List<CmsPath> calls);

}
