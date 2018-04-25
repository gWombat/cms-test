package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.context.ConfigurationContext;

public class JackrabbitConfigurationContext extends ConfigurationContext {

    private String language;
    private String brandNode;
    private String brandNodeSpecific;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBrandNode() {
        return brandNode;
    }

    public void setBrandNode(String brandNode) {
        this.brandNode = brandNode;
    }

    public String getBrandNodeSpecific() {
        return brandNodeSpecific;
    }

    public void setBrandNodeSpecific(String brandNodeSpecific) {
        this.brandNodeSpecific = brandNodeSpecific;
    }
}
