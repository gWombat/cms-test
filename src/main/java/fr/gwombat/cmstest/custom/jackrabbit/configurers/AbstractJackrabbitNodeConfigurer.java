package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.CmsCallConfig;
import fr.gwombat.cmstest.core.configurers.CmsCallConfigurer;
import fr.gwombat.cmstest.core.path.CmsPath;
import fr.gwombat.cmstest.custom.jackrabbit.JackrabbitConfigurationContext;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPath;
import fr.gwombat.cmstest.custom.jackrabbit.path.JackrabbitPathBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractJackrabbitNodeConfigurer implements CmsCallConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJackrabbitNodeConfigurer.class);

    private CmsConfigurer cmsConfigurer;

    protected void configureCallRecursive(List<CmsCallConfig> cmsCallConfigList, List<CmsPath> cmsPathList, JackrabbitConfigurationContext context, String rootName, final JackrabbitPathBuilder parentPathBuilder, boolean specificNode) {
        if (cmsCallConfigList == null || cmsCallConfigList.isEmpty())
            return;

        for (CmsCallConfig cmsCallConfig : cmsCallConfigList) {
            final JackrabbitPathBuilder jackrabbitPathBuilder = internalInitPathBuilder(rootName, parentPathBuilder)
                    .brand(specificNode ? context.getBrandNodeSpecific() : context.getBrandNode())
                    .language(context.getLanguage())
                    .addPath(cmsCallConfig.getPath())
                    .dynamicVariables(context.getDynamicContext().getDynamicPathVariables());

            final JackrabbitPath jackrabbitPath = jackrabbitPathBuilder.build();
            LOGGER.debug("Adding call {} to the queue", jackrabbitPath);
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
