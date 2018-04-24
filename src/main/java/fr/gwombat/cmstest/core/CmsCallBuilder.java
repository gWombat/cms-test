package fr.gwombat.cmstest.core;

import java.util.List;

public final class CmsCallBuilder {

    public static final String DYNAMIC_VARIABLE_PREFIX = "${";
    public static final String DYNAMIC_VARIABLE_SUFFIX = "}";

    private StringBuilder       stringBuilder;
    private List<CmsCallConfig> childCalls;

    private CmsCallBuilder(final String path) {
        this.stringBuilder = new StringBuilder(path);
    }

    public static CmsCallBuilder init(String path) {
        return new CmsCallBuilder(path);
    }

    public CmsCallBuilder appendPath(final String path) {
        this.stringBuilder.append(path);
        return this;
    }

    public CmsCallBuilder withDynamicVariable(final String key) {
        this.stringBuilder.append(buildAndGetDynamicVariable(key));
        return this;
    }

    public CmsCallBuilder childCalls(final List<CmsCallConfig> childCalls) {
        this.childCalls = childCalls;
        return this;
    }

    public CmsCallConfig build() {
        final CmsCallConfig cmsCallConfig = new CmsCallConfig();
        cmsCallConfig.setPath(this.stringBuilder.toString());
        cmsCallConfig.setChildCalls(this.childCalls);

        return cmsCallConfig;
    }

    public static String buildAndGetDynamicVariable(final String key) {
        return DYNAMIC_VARIABLE_PREFIX + key + DYNAMIC_VARIABLE_SUFFIX;
    }

}
