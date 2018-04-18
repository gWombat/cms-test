package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsConfiguration;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

public interface CallConfigurationChain {

    void configure(CmsConfiguration cmsConfiguration, List<CmsPath> calls);

}
