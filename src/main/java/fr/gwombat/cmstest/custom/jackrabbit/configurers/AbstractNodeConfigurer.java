package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCall;
import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitCallWrapper;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitLocalContext;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPath;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPathBuilder;

import java.util.List;

public abstract class AbstractNodeConfigurer extends AbstractCallConfigurer<JackrabbitCallWrapper> {

    private CmsConfigurer cmsConfigurer;

    protected void configureCallRecursive(List<CmsCall> cmsCallList, List<CmsPath> cmsPathList, JackrabbitLocalContext context, String rootName, final JackrabbitPathBuilder parentPathBuilder, boolean specificNode) {
        if (cmsCallList == null || cmsCallList.isEmpty())
            return;

        for (CmsCall cmsCall : cmsCallList) {
            final JackrabbitPathBuilder jackrabbitPathBuilder = internalInitPathBuilder(rootName, parentPathBuilder)
                    .brand(specificNode ? context.getBrandNodeSpecific() : context.getBrandNode())
                    .language(context.getLanguage())
                    .addPath(cmsCall.getPath())
                    .withCityIdIfNecessary(cmsCall.isAppendCityToPath(), context.getDepartureCityId());

            final JackrabbitPath jackrabbitPath = jackrabbitPathBuilder.build();
            cmsPathList.add(jackrabbitPath);

            if (cmsCall.getChildCalls() != null)
                configureCallRecursive(cmsCall.getChildCalls(), cmsPathList, context, null, jackrabbitPathBuilder, specificNode);
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
