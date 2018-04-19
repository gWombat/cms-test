package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallWrapper;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

public abstract class AbstractCallConfigurer<T extends CmsCallWrapper> {

    protected abstract boolean isExecutable(T cmsCallWrapper);

    protected abstract void configure(T cmsCallWrapper, List<CmsPath> calls, LocalContext context);

}
