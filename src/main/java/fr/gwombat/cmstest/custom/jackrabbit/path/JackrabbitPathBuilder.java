package fr.gwombat.cmstest.custom.jackrabbit.path;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JackrabbitPathBuilder {

    private final StringBuilder       pathBuilder;
    private       String              language;
    private       String              brand;
    private final String              separator;
    private       Map<String, String> dynamicVariables;

    private JackrabbitPathBuilder(final String rootNode, final String separator) {
        pathBuilder = new StringBuilder();
        this.separator = separator;
        this.dynamicVariables = new HashMap<>(0);
        addPath(rootNode);
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

    public JackrabbitPathBuilder dynamicVariables(final Map<String, String> dynamicVariables) {
        this.dynamicVariables = dynamicVariables;
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
        return resolveDynamicVariables(path, false);
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

    private String hideDynamicValues(final String path) {
        return resolveDynamicVariables(path, true);
    }

    /*
     * based on https://stackoverflow.com/a/959776/5695673
     * no 3rd party library (e.g. apache StrSubstitutor)...
     */
    private String resolveDynamicVariables(String path, boolean hideVariables) {
        final Pattern pattern = Pattern.compile("\\$?\\{(.+?)}");
        final Matcher matcher = pattern.matcher(path);
        final StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String replacement;
            if (hideVariables)
                replacement = "";
            else
                replacement = dynamicVariables.get(matcher.group(1));

            builder.append(path, i, matcher.start());
            if (replacement == null)
                builder.append(matcher.group(0));
            else
                builder.append(replacement);
            i = matcher.end();
        }
        builder.append(path, i, path.length());
        return builder.toString();

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