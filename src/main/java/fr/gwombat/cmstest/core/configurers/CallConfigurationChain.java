package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallWrapper;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

public interface CallConfigurationChain {

    void configure(CmsCallWrapper cmsCallWrapper, List<CmsPath> calls, LocalContext localContext);

}
