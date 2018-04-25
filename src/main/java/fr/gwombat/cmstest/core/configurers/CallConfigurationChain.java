package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;

import java.util.List;

public interface CallConfigurationChain {

    <T extends CmsCallConfigWrapper> void configure(T cmsCallConfigWrapper, List<CmsPath> calls, ConfigurationContext configurationContext) throws CmsConfigurationException;

}
