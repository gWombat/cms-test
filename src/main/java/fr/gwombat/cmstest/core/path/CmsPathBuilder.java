package fr.gwombat.cmstest.core.path;

import java.text.MessageFormat;

public final class CmsPathBuilder {

    private StringBuilder pathBuilder;
    private String        language;
    private String        brand;
    private String        separator;

    private CmsPathBuilder(final String rootNode, final String separator) {
        pathBuilder = new StringBuilder();
        this.separator = separator;
        addPath(rootNode);
    }

    public static CmsPathBuilder init(final String rootNode, final String separator) {
        return new CmsPathBuilder(rootNode, separator);
    }

    public CmsPathBuilder addPath(final String path) {
        if (path != null && !"".equals(path)) {
            pathBuilder.append(path);
            addTrailingSlash();
        }
        return this;
    }

    public CmsPathBuilder addPath(final String path, final Object... args) {
        if (path != null && !"".equals(path)) {
            final String formattedPath = MessageFormat.format(path, args);
            return addPath(formattedPath);
        }
        return this;
    }

    public CmsPathBuilder withCityId(final Integer cityId, final String separator) {
        if (cityId != null) {
            removeTrailingSlash();
            pathBuilder.append(separator);
            pathBuilder.append(String.valueOf(cityId));
            addTrailingSlash();
        }
        return this;
    }

    public CmsPathBuilder addProperty(final String propertyName) {
        if (propertyName != null && !"".equals(propertyName))
            pathBuilder.append(propertyName);
        return this;
    }

    public CmsPathBuilder language(final String language) {
        if (language != null && !"".equals(language))
            this.language = language;
        return this;
    }

    public CmsPathBuilder brand(final String brand) {
        if (brand != null && !"".equals(brand))
            this.brand = brand;
        return this;
    }

    public CmsPathBuilder withCityId(final Integer cityId) {
        return withCityId(cityId, "-");
    }

    public CmsPath build() {
        return new CmsPath(this);
    }

    String buildCmsPath() {
        final String path = validateAndGetPath();
        if (path.isEmpty())
            return null;
        return path;
    }

    String buildFullPath() {
        final String prefix = buildPrefix();
        return prefix + buildCmsPath();
    }

    private String buildPrefix() {
        final StringBuilder prefix = new StringBuilder();
        if (language != null && !"".equals(language))
            prefix.append(language).append(separator);
        if (brand != null && !"".equals(brand))
            prefix.append(brand).append(separator);
        return prefix.toString();
    }

    private String validateAndGetPath() {
        String path = pathBuilder.toString();
        if (!"".equals(path)) {
            path = path.trim();
            removeLeadingSlash(pathBuilder);
            removeTrailingSlash();
        }
        return path;
    }

    private void removeLeadingSlash(final StringBuilder stringBuilder) {
        if (stringBuilder.toString().startsWith(separator))
            stringBuilder.deleteCharAt(0);
    }

    private void addTrailingSlash() {
        if (!pathBuilder.toString().endsWith(separator))
            pathBuilder.append(separator);
    }

    private void removeTrailingSlash() {
        if (pathBuilder.toString().endsWith(separator))
            pathBuilder.deleteCharAt(pathBuilder.length() - 1);
    }

    @Override
    public String toString() {
        return buildFullPath();
    }

    String getLanguage() {
        return language;
    }

    String getBrand() {
        return brand;
    }
}