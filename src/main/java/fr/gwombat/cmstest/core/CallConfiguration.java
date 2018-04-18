package fr.gwombat.cmstest.core;

import java.util.List;

public class CallConfiguration {

    private String                  path;
    private boolean                 appendCityToPath;
    private List<CallConfiguration> childCalls;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isAppendCityToPath() {
        return appendCityToPath;
    }

    public void setAppendCityToPath(boolean appendCityToPath) {
        this.appendCityToPath = appendCityToPath;
    }

    public List<CallConfiguration> getChildCalls() {
        return childCalls;
    }

    public void setChildCalls(List<CallConfiguration> childCalls) {
        this.childCalls = childCalls;
    }
}
