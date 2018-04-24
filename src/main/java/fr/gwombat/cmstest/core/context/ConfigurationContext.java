package fr.gwombat.cmstest.core.context;

public class ConfigurationContext {

    private String         rootNodePath;
    private DynamicContext dynamicContext;

    public DynamicContext getDynamicContext() {
        return dynamicContext;
    }

    public void setDynamicContext(DynamicContext dynamicContext) {
        this.dynamicContext = dynamicContext;
    }

    public String getRootNodePath() {
        return rootNodePath;
    }

    public void setRootNodePath(String rootNodePath) {
        this.rootNodePath = rootNodePath;
    }
}
