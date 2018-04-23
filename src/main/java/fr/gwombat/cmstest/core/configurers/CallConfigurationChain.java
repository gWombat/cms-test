package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

public interface CallConfigurationChain {

    void configure(CmsCallConfigWrapper cmsCallConfigWrapper, List<CmsPath> calls, LocalContext localContext);

}
