package fr.gwombat.cmstest.custom.jackrabbit;

import fr.gwombat.cmstest.core.context.LocalContext;

class JackrabbitLocalContext extends LocalContext {

    private Long   departureCityId;
    private String language;
    private String brandNode;
    private String brandNodeSpecific;

    Long getDepartureCityId() {
        return departureCityId;
    }

    void setDepartureCityId(Long departureCityId) {
        this.departureCityId = departureCityId;
    }

    String getLanguage() {
        return language;
    }

    void setLanguage(String language) {
        this.language = language;
    }

    String getBrandNode() {
        return brandNode;
    }

    void setBrandNode(String brandNode) {
        this.brandNode = brandNode;
    }

    String getBrandNodeSpecific() {
        return brandNodeSpecific;
    }

    void setBrandNodeSpecific(String brandNodeSpecific) {
        this.brandNodeSpecific = brandNodeSpecific;
    }
}
