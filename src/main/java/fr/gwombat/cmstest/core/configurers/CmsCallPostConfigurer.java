package fr.gwombat.cmstest.core.configurers;

import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

@FunctionalInterface
public interface CmsCallPostConfigurer {

    void postConfigure(final List<CmsPath> calls);

}
