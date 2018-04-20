package fr.gwombat.cmstest.core;

import java.util.ArrayList;
import java.util.List;

public class CmsCall {

    private String        path;
    private boolean       appendCityToPath;
    private List<CmsCall> childCalls;

    public CmsCall() {
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

    public List<CmsCall> getChildCalls() {
        return childCalls;
    }

    public void setChildCalls(List<CmsCall> childCalls) {
        this.childCalls = childCalls;
    }
}
