package fr.gwombat.cmstest.core;

import java.util.List;

public final class CmsCallBuilder {

    private String              path;
    private boolean             appendCityToPath;
    private List<CmsCallConfig> childCalls;

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

    public CmsCallBuilder childCalls(final List<CmsCallConfig> childCalls) {
        this.childCalls = childCalls;
        return this;
    }

    public CmsCallConfig build() {
        final CmsCallConfig cmsCallConfig = new CmsCallConfig();
        cmsCallConfig.setPath(this.path);
        cmsCallConfig.setAppendCityToPath(this.appendCityToPath);
        cmsCallConfig.setChildCalls(this.childCalls);

        return cmsCallConfig;
    }

}
