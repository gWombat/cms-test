package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.core.configurers.AbstractCallConfigurer;
import fr.gwombat.cmstest.mapping.registry.CallConfigurerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractJackrabbitConfigurer implements CmsConfigurer {

    @Autowired
    private AbstractCallConfigurer<?> defaultNodesConfigurer;
    @Autowired
    private AbstractCallConfigurer<?> specificNodesConfigurer;

    @Override
    public String getRootNodePrefix() {
        return getLanguage() + getPropertySeparator() + getBrandNode() + getPropertySeparator();
    }

    @Override
    public void addCallConfigurers(CallConfigurerRegistry callConfigurerRegistry) {
        callConfigurerRegistry
                .addCallConfigurer(defaultNodesConfigurer)
                .addCallConfigurer(specificNodesConfigurer);

    }

    public abstract String getLanguage();

    public abstract String getBrandNode();

    public abstract String getBrandNodeSpecific();

    public abstract String getWorkspace();

}
