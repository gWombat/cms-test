package fr.gwombat.cmstest.custom.jackrabbit.configurers;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.mapping.registry.CallConfigurerRegistry;

public abstract class AbstractJackrabbitConfigurer extends JackrabbitConfigurerSupport implements CmsConfigurer {

    @Override
    public String getRootNodePrefix() {
        return getLanguage() + getPropertySeparator() + getBrandNode() + getPropertySeparator();
    }

    @Override
    public void addCallConfigurers(CallConfigurerRegistry callConfigurerRegistry) {
        callConfigurerRegistry.addCallConfigurers(nodesConfigurers());
    }

    public abstract String getLanguage();

    public abstract String getBrandNode();

    public abstract String getBrandNodeSpecific();

    public abstract String getWorkspace();


}
