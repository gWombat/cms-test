package fr.gwombat.cmstest.core;

import java.util.ArrayList;
import java.util.List;

public class CmsCallConfig {

    private String              path;
    private boolean             appendCityToPath;
    private List<CmsCallConfig> childCalls;

    public CmsCallConfig() {
        this.childCalls = new ArrayList<>(0);
    }

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

    public List<CmsCallConfig> getChildCalls() {
        return childCalls;
    }

    public void setChildCalls(List<CmsCallConfig> childCalls) {
        this.childCalls = childCalls;
    }
}
