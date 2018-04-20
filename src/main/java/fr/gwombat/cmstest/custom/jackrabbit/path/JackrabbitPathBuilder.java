package fr.gwombat.cmstest.custom.jackrabbit.path;

import java.util.LinkedList;
import java.util.Queue;

public final class JackrabbitPathBuilder {

    private static final char SPECIAL_CHARACTER = '#';

    private StringBuilder pathBuilder;
    private String        language;
    private String        brand;
    private String        separator;
    private Queue<String> dynamicValues;

    private JackrabbitPathBuilder(final String rootNode, final String separator) {
        pathBuilder = new StringBuilder();
        this.separator = separator;
        this.dynamicValues = new LinkedList<>();
        addPath(rootNode);
    }

    private static JackrabbitPathBuilder from(final JackrabbitPathBuilder other) {
        JackrabbitPathBuilder jackrabbitPathBuilder = new JackrabbitPathBuilder(null, other.separator);
        jackrabbitPathBuilder.pathBuilder = other.pathBuilder;
        jackrabbitPathBuilder.language = other.language;
        jackrabbitPathBuilder.brand = other.brand;
        jackrabbitPathBuilder.dynamicValues = other.dynamicValues;

        return jackrabbitPathBuilder;
    }

    public static JackrabbitPathBuilder init(final String rootNode, final String separator) {
        return new JackrabbitPathBuilder(rootNode, separator);
    }

    public JackrabbitPathBuilder addPath(final String path) {
        if (path != null && !"".equals(path)) {
            pathBuilder.append(path);
            addTrailingSlash();
        }
        return this;
    }

    public JackrabbitPathBuilder addProperty(final String propertyName) {
        if (propertyName != null && !"".equals(propertyName))
            pathBuilder.append(propertyName);
        return this;
    }

    public JackrabbitPathBuilder language(final String language) {
        if (language != null && !"".equals(language))
            this.language = language;
        return this;
    }

    public JackrabbitPathBuilder brand(final String brand) {
        if (brand != null && !"".equals(brand))
            this.brand = brand;
        return this;
    }

    public JackrabbitPathBuilder withCityIdIfNecessary(boolean isNecessary, Long cityId) {
        if (isNecessary)
            return withCityId(cityId, "");
        return this;
    }

    public JackrabbitPathBuilder withCityId(final Long cityId, final String separator) {
        if (cityId != null) {
            removeTrailingSlash();
            pathBuilder.append(separator);
            pathBuilder.append(SPECIAL_CHARACTER);
            dynamicValues.add(String.valueOf(cityId));
            addTrailingSlash();
        }
        return this;
    }

    public JackrabbitPath build() {
        return new JackrabbitPath(this);
    }

    String buildFullPath() {
        final String prefix = buildPrefix();
        final String path = buildResolvedPath();
        return prefix + path;
    }

    private String buildPrefix() {
        final StringBuilder prefix = new StringBuilder();
        if (language != null && !"".equals(language))
            prefix.append(language).append(separator);
        if (brand != null && !"".equals(brand))
            prefix.append(brand).append(separator);
        return prefix.toString();
    }

    String buildCmsPath() {
        final String path = validateAndGetPath();
        if (path.isEmpty())
            return null;
        return hideDynamicValues(path);
    }

    String buildResolvedPath() {
        final String path = validateAndGetPath();
        if (path.isEmpty())
            return null;
        return resolveDynamicValues(path);
    }

    private String validateAndGetPath() {
        String path = pathBuilder.toString();
        if (!"".equals(path)) {
            path = path.trim();
            path = removeLeadingSlash(path);
            path = removeTrailingSlash(path);
        }
        return path;
    }

    private static String hideDynamicValues(final String path) {
        return path.replaceAll(String.valueOf(SPECIAL_CHARACTER), "");
    }

    private String resolveDynamicValues(String path) {
        if (path == null)
            return null;

        final StringBuilder resolvedPath = new StringBuilder();
        final Queue<String> localDynamicValues = new LinkedList<>(dynamicValues);
        for (char c : path.toCharArray())
            if (c == SPECIAL_CHARACTER)
                resolvedPath.append(localDynamicValues.poll());
            else
                resolvedPath.append(c);
        return resolvedPath.toString();
    }

    private String removeLeadingSlash(final String path) {
        if (path.startsWith(separator))
            return path.substring(1);
        return path;
    }

    private void addTrailingSlash() {
        if (!pathBuilder.toString().endsWith(separator))
            pathBuilder.append(separator);
    }

    private void removeTrailingSlash() {
        if (pathBuilder.toString().endsWith(separator))
            pathBuilder.deleteCharAt(pathBuilder.length() - 1);
    }

    private String removeTrailingSlash(final String path) {
        if (path.endsWith(separator))
            return path.substring(0, path.length() - 1);
        return path;
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