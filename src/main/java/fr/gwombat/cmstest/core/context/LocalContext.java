package fr.gwombat.cmstest.core.context;

public class LocalContext {

    private String  rootNodePath;
    private Integer departureCityId;
    private String  language;
    private String  brandNode;
    private String  brandNodeSpecific;

    public String getRootNodePath() {
        return rootNodePath;
    }

    public void setRootNodePath(String rootNodePath) {
        this.rootNodePath = rootNodePath;
    }

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

    public Integer getDepartureCityId() {
        return departureCityId;
    }

    public void setDepartureCityId(Integer departureCityId) {
        this.departureCityId = departureCityId;
    }
}
