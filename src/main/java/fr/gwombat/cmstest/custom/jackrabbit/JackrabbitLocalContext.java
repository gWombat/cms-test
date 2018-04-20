package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.context.LocalContext;

public class JackrabbitLocalContext extends LocalContext {

    private Long   departureCityId;
    private String language;
    private String brandNode;
    private String brandNodeSpecific;

    public Long getDepartureCityId() {
        return departureCityId;
    }

    void setDepartureCityId(Long departureCityId) {
        this.departureCityId = departureCityId;
    }

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
