package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.ConfigurationContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.exceptions.CmsConfigurationException;

import java.util.List;

public abstract class AbstractCallConfigurer<T extends CmsCallConfigWrapper> {

    protected abstract boolean isExecutable(T cmsCallWrapper);

    protected abstract void configure(T cmsCallWrapper, List<CmsPath> calls, ConfigurationContext context) throws CmsConfigurationException;

}
