package fr.gwombat.cmstest.custom.jackrabbit.path;

import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.Objects;

public class JackrabbitPath implements CmsPath {

    private final String language;
    private final String brand;
    private final String path;
    private final String resolvedPath;
    private final String fullCmsPath;

    JackrabbitPath(final JackrabbitPathBuilder builder) {
        this.path = builder.buildCmsPath();
        this.resolvedPath = builder.buildResolvedPath();
        this.fullCmsPath = builder.buildFullPath();
        this.brand = builder.getBrand();
        this.language = builder.getLanguage();
    }

    @Override
    public String toString() {
        return getFullCmsPath();
    }

    /**
     * @return the fullCmsPath
     */
    public String getFullCmsPath() {
        return fullCmsPath;
    }

    public String getLanguage() {
        return language;
    }

    public String getBrand() {
        return brand;
    }

    public String getPath() {
        return path;
    }

    public String getResolvedPath() {
        return resolvedPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JackrabbitPath jackrabbitPath = (JackrabbitPath) o;
        return Objects.equals(fullCmsPath, jackrabbitPath.fullCmsPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullCmsPath);
    }
}

