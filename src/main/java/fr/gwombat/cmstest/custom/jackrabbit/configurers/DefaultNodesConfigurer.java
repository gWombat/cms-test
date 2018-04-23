package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitCallConfigWrapper;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitLocalContext;

import java.util.List;

public class DefaultNodesConfigurer extends AbstractNodeConfigurer {

    @Override
    protected boolean isExecutable(JackrabbitCallConfigWrapper cmsCallWrapper) {
        return cmsCallWrapper.isCallDefaultNodes();
    }

    @Override
    protected void configure(JackrabbitCallConfigWrapper cmsCallWrapper, List<CmsPath> calls, LocalContext context) {
        final JackrabbitLocalContext localContext = (JackrabbitLocalContext) context;
        configureCallRecursive(cmsCallWrapper.getCalls(), calls, localContext, cmsCallWrapper.getRootNodePath(), null, false);
    }
}
