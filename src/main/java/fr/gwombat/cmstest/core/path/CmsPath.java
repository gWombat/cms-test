package fr.gwombat.cmstest.core.path;

import java.util.Objects;

public class CmsPath {

    private final String language;
    private final String brand;
    private final String path;
    private final String fullCmsPath;

    CmsPath(final CmsPathBuilder builder) {
        this.path = builder.buildCmsPath();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmsPath cmsPath = (CmsPath) o;
        return Objects.equals(fullCmsPath, cmsPath.fullCmsPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullCmsPath);
    }
}

