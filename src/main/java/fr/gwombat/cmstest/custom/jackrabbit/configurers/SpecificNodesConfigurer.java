package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitCallWrapper;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitLocalContext;

import java.util.List;

public class SpecificNodesConfigurer extends AbstractNodeConfigurer {

    @Override
    protected boolean isExecutable(JackrabbitCallWrapper cmsCallWrapper) {
        return cmsCallWrapper.isCallSpecificNodes();
    }

    @Override
    protected void configure(JackrabbitCallWrapper cmsCallWrapper, List<CmsPath> calls, LocalContext context) {
        final JackrabbitLocalContext localContext = (JackrabbitLocalContext) context;
        configureCallRecursive(cmsCallWrapper.getCalls(), calls, localContext, cmsCallWrapper.getRootNodePath(), null, true);
    }
}
