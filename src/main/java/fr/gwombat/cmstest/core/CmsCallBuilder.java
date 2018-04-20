package fr.gwombat.cmstest.core;

import java.util.List;

public final class CmsCallBuilder {

    private String        path;
    private boolean       appendCityToPath;
    private List<CmsCall> childCalls;

    private CmsCallBuilder() {
    }

    public static CmsCallBuilder init() {
        return new CmsCallBuilder();
    }

    public CmsCallBuilder path(final String path) {
        this.path = path;
        return this;
    }

    public CmsCallBuilder appendCityToPath() {
        return appendCityToPath(true);
    }

    public CmsCallBuilder appendCityToPath(final boolean appendCityToPath) {
        this.appendCityToPath = appendCityToPath;
        return this;
    }

    public CmsCallBuilder childCalls(final List<CmsCall> childCalls) {
        this.childCalls = childCalls;
        return this;
    }

    public CmsCall build() {
        final CmsCall cmsCall = new CmsCall();
        cmsCall.setPath(this.path);
        cmsCall.setAppendCityToPath(this.appendCityToPath);
        cmsCall.setChildCalls(this.childCalls);

        return cmsCall;
    }

}
