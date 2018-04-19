package fr.gwombat.cmstest.core;

import java.util.ArrayList;
import java.util.List;

public class CmsCallWrapper {

    private String        rootNodePath;
    private List<CmsCall> calls;

    public CmsCallWrapper() {
        calls = new ArrayList<>(0);
    }

    public CmsCallWrapper addCall(CmsCall cmsCall) {
        if (cmsCall != null)
            calls.add(cmsCall);
        return this;
    }

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
