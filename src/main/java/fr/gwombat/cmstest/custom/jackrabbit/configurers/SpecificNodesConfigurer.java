package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitCallConfigWrapper;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitConfigurationContext;

import java.util.List;

public class SpecificNodesConfigurer extends AbstractJackrabbitCallConfigurer {

    @Override
    public boolean isExecutable(CmsCallConfigWrapper cmsCallWrapper) {
        return ((JackrabbitCallConfigWrapper) cmsCallWrapper).isCallSpecificNodes();
    }

    @Override
    public void configure(CmsCallConfigWrapper cmsCallWrapper, List<CmsPath> calls, ConfigurationContext context) {
        final JackrabbitConfigurationContext localContext = (JackrabbitConfigurationContext) context;
        configureCallRecursive(cmsCallWrapper.getCalls(), calls, localContext, cmsCallWrapper.getRootNodePath(), null, true);
    }
}
