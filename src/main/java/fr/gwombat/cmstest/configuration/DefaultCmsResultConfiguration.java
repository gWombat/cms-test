package fr.gwombat.cmstest.configuration;

public class DefaultCmsResultConfiguration implements CmsResultConfiguration {


    @Override
    public String getRootNodeName() {
        return "repo";
    }

    @Override
    public String getLanguage() {
        return "fr";
    }

    @Override
    public String getRootNodePrefix() {
        return getRootNodeName() + getPropertySeparator() + getLanguage() + getPropertySeparator();
    }
}
