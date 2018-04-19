package fr.gwombat.cmstest.core;

import java.util.List;

public class CmsConfiguration {

    private String        rootNodePath;
    private List<CmsCall> calls;

    public List<CmsCall> getCalls() {
        return calls;
    }

    public void setCalls(List<CmsCall> calls) {
        this.calls = calls;
    }

    public String getRootNodePath() {
        return rootNodePath;
    }

    public void setRootNodePath(String rootNodePath) {
        this.rootNodePath = rootNodePath;
    }
}
