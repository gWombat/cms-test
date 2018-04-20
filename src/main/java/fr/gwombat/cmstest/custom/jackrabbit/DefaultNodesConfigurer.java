package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCall;
import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.core.context.LocalContext;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.path.CmsPathBuilder;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPath;

import java.util.List;

public class DefaultNodesConfigurer extends AbstractCallConfigurer<JackrabbitCallWrapper> {

    private CmsConfigurer cmsConfigurer;

    @Override
    protected boolean isExecutable(JackrabbitCallWrapper cmsCallWrapper) {
        return cmsCallWrapper.isCallDefaultNodes();
    }

    @Override
    protected void configure(JackrabbitCallWrapper cmsCallWrapper, List<CmsPath> calls, LocalContext context) {
        final JackrabbitLocalContext localContext = (JackrabbitLocalContext) context;
        configureCallInternal(cmsCallWrapper.getCalls(), calls, localContext, cmsCallWrapper.getRootNodePath());
    }

    private void configureCallInternal(List<CmsCall> cmsCallList, List<CmsPath> cmsPathList, JackrabbitLocalContext context, String rootName) {

        if (cmsCallList == null || cmsCallList.isEmpty())
            return;

        for (CmsCall cmsCall : cmsCallList) {
            final JackrabbitPath jackrabbitPath = CmsPathBuilder.init(rootName, cmsConfigurer.getPropertySeparator())
                    .brand(context.getBrandNode())
                    .language(context.getLanguage())
                    .addPath(cmsCall.getPath())
                    .withCityIdIfNecessary(cmsCall.isAppendCityToPath(), context.getDepartureCityId())
                    .build();
            cmsPathList.add(jackrabbitPath);
            if (cmsCall.getCalls() != null)
                configureCallInternal(cmsCall.getCalls(), cmsPathList, context, jackrabbitPath.getPath());
        }
    }

    void setCmsConfigurer(CmsConfigurer cmsConfigurer) {
        this.cmsConfigurer = cmsConfigurer;
    }
}
