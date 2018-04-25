package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitCallConfigWrapper;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitConfigurationContext;

import java.util.List;

public class DefaultNodesConfigurer extends AbstractJackrabbitNodeConfigurer {

    @Override
    public boolean isExecutable(JackrabbitCallConfigWrapper cmsCallWrapper) {
        return cmsCallWrapper.isCallDefaultNodes();
    }

    @Override
    public void configure(JackrabbitCallConfigWrapper cmsCallWrapper, List<CmsPath> calls, ConfigurationContext context) {
        final JackrabbitConfigurationContext localContext = (JackrabbitConfigurationContext) context;
        configureCallRecursive(cmsCallWrapper.getCalls(), calls, localContext, cmsCallWrapper.getRootNodePath(), null, false);
    }
}
