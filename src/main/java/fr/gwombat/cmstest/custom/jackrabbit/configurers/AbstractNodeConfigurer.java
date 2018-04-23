package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallConfig;
import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitCallConfigWrapper;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitLocalContext;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPath;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPathBuilder;

import java.util.List;

public abstract class AbstractNodeConfigurer extends AbstractCallConfigurer<JackrabbitCallConfigWrapper> {

    private CmsConfigurer cmsConfigurer;

    protected void configureCallRecursive(List<CmsCallConfig> cmsCallConfigList, List<CmsPath> cmsPathList, JackrabbitLocalContext context, String rootName, final JackrabbitPathBuilder parentPathBuilder, boolean specificNode) {
        if (cmsCallConfigList == null || cmsCallConfigList.isEmpty())
            return;

        for (CmsCallConfig cmsCallConfig : cmsCallConfigList) {
            final JackrabbitPathBuilder jackrabbitPathBuilder = internalInitPathBuilder(rootName, parentPathBuilder)
                    .brand(specificNode ? context.getBrandNodeSpecific() : context.getBrandNode())
                    .language(context.getLanguage())
                    .addPath(cmsCallConfig.getPath())
                    .withCityIdIfNecessary(cmsCallConfig.isAppendCityToPath(), context.getDepartureCityId());

            final JackrabbitPath jackrabbitPath = jackrabbitPathBuilder.build();
            cmsPathList.add(jackrabbitPath);

            if (cmsCallConfig.getChildCalls() != null)
                configureCallRecursive(cmsCallConfig.getChildCalls(), cmsPathList, context, null, jackrabbitPathBuilder, specificNode);
        }
    }

    private JackrabbitPathBuilder internalInitPathBuilder(String rootName, JackrabbitPathBuilder parentPathBuilder) {
        JackrabbitPathBuilder jackrabbitPathBuilder;
        if (parentPathBuilder != null)
            jackrabbitPathBuilder = parentPathBuilder;
        else
            jackrabbitPathBuilder = JackrabbitPathBuilder.init(rootName, cmsConfigurer.getPropertySeparator());
        return jackrabbitPathBuilder;
    }

    public void setCmsConfigurer(CmsConfigurer cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }

}
