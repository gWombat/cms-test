package fr.gwombat.cmstest.core;

import java.util.ArrayList;
import java.util.List;

public class CmsCallConfigWrapper {

    private String              rootNodePath;
    private List<CmsCallConfig> calls;

    public CmsCallConfigWrapper() {
        calls = new ArrayList<>(0);
    }

    public CmsCallConfigWrapper addCall(CmsCallConfig cmsCallConfig) {
        if (cmsCallConfig != null)
            calls.add(cmsCallConfig);
        return this;
    }

    public List<CmsCallConfig> getCalls() {
        return calls;
    }

    public void setCalls(List<CmsCallConfig> calls) {
        this.calls = calls;
    }

    public String getRootNodePath() {
        return rootNodePath;
    }

    public void setRootNodePath(String rootNodePath) {
        this.rootNodePath = rootNodePath;
    }
}
