package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.context.ConfigurationContext;

public class JackrabbitConfigurationContext extends ConfigurationContext {

    private String language;
    private String brandNode;
    private String brandNodeSpecific;

    public String getLanguage() {
        return language;
    }

    void setLanguage(String language) {
        this.language = language;
    }

    public String getBrandNode() {
        return brandNode;
    }

    void setBrandNode(String brandNode) {
        this.brandNode = brandNode;
    }

    public String getBrandNodeSpecific() {
        return brandNodeSpecific;
    }

    void setBrandNodeSpecific(String brandNodeSpecific) {
        this.brandNodeSpecific = brandNodeSpecific;
    }
}
