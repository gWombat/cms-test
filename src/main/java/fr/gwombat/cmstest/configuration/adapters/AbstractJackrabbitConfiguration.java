package fr.gwombat.cmstest.configuration.adapters;

import fr.gwombat.cmstest.configuration.CmsResultConfigurer;

public abstract class AbstractJackrabbitConfiguration implements CmsResultConfigurer {

    @Override
    public String getRootNodePrefix() {
        return getLanguage() + getPropertySeparator() + getBrandNode() + getPropertySeparator();
    }

    public abstract String getLanguage();
    public abstract String getBrandNode();
    public abstract String getBrandNodeSpecific();
    public abstract String getWorkspace();

}
