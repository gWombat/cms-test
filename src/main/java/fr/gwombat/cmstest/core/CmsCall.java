package fr.gwombat.cmstest.core;

import java.util.ArrayList;
import java.util.List;

public class CmsCall {

    private String        path;
    private boolean       appendCityToPath;
    private List<CmsCall> calls;

    public CmsCall() {
        this.calls = new ArrayList<>(0);
    }

    public CmsCall addCall(CmsCall call){
        this.calls.add(call);
        return this;
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

    public List<CmsCall> getCalls() {
        return calls;
    }

    public void setCalls(List<CmsCall> calls) {
        this.calls = calls;
    }
}
