package fr.gwombat.cmstest.utils;

import fr.gwombat.cmstest.annotations.CmsProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public final class CmsProcessorUtils {

    public static final String CMS_LANGUAGE = "fr";
    public static final String CMS_REPO     = "repo";

    private CmsProcessorUtils() {
    }

    public static String detectPropertyName(final Field field) {
        final CmsProperty annotation = field.getAnnotation(CmsProperty.class);
        if (annotation != null
                && !annotation.name().equals(""))
            return annotation.name();
        return toCamelCase(field.getName());
    }

    public static String detectPropertyName(final Method method) {
        final CmsProperty annotation = method.getAnnotation(CmsProperty.class);
        if (annotation != null
                && !annotation.name().equals(""))
            return annotation.name();
        return null;
    }

    public static String detectRootNodeName(final Class<?> resultType) {
        final CmsProperty annotation = resultType.getAnnotation(CmsProperty.class);
        if (annotation != null
                && !annotation.name().equals(""))
            return annotation.name();
        return toCamelCase(resultType.getSimpleName());
    }

    private static String toCamelCase(String value) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (i == 0)
                stringBuilder.append(Character.toLowerCase(c));
            else
                stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static boolean isCollection(final Class<?> parameterType) {
        return Collection.class.isAssignableFrom(parameterType);
    }

    public static boolean isMap(final Class<?> parameterType) {
        return Map.class.isAssignableFrom(parameterType);
    }

    public static boolean isSimpleType(final Class<?> parameterType) {
        if (parameterType == Byte.class || parameterType == byte.class)
            return true;
        if (parameterType == Float.class || parameterType == float.class)
            return true;
        if (parameterType == Integer.class || parameterType == int.class)
            return true;
        if (parameterType == Double.class || parameterType == double.class)
            return true;
        if (parameterType == Long.class || parameterType == long.class)
            return true;
        if (parameterType == Boolean.class || parameterType == boolean.class)
            return true;
        if (parameterType == Short.class || parameterType == short.class)
            return true;
        if (parameterType == String.class)
            return true;
        if (parameterType == Character.class || parameterType == char.class)
            return true;
        return false;
    }

    public static Object castValue(final Class<?> parameterType, final String cmsValue) {
        if (parameterType == Byte.class || parameterType == byte.class)
            return Byte.parseByte(cmsValue);
        if (parameterType == Float.class || parameterType == float.class)
            return Float.parseFloat(cmsValue);
        if (parameterType == Integer.class || parameterType == int.class)
            return Integer.parseInt(cmsValue);
        if (parameterType == Double.class || parameterType == double.class)
            return Double.parseDouble(cmsValue);
        if (parameterType == Long.class || parameterType == long.class)
            return Long.parseLong(cmsValue);
        if (parameterType == Boolean.class || parameterType == boolean.class)
            return Boolean.parseBoolean(cmsValue);
        if (parameterType == Short.class || parameterType == short.class)
            return Short.parseShort(cmsValue);

        return parameterType.cast(cmsValue);
    }

    /**
     * https://stackoverflow.com/a/28857184/5695673
     * No JAVA8!!!!
     *
     * @param globalResults
     * @param prefix
     * @return
     */
    public static Map<String, String> getCmsResultsSubMap(final Map<String, String> globalResults, final String prefix) {
        final Map<String, String> results = new HashMap<>(0);
        for (Map.Entry<String, String> entry : globalResults.entrySet())
            if (entry.getKey().startsWith(buildCmsPath(prefix)))
                results.put(extractFromKey(entry.getKey(), prefix), entry.getValue());

        return results;
    }

    private static String buildCmsPath(final String path) {
        return CMS_REPO + "/" + CMS_LANGUAGE + "/" + path;
    }

    private static String extractFromKey(final String fullKey, final String prefix) {
        return fullKey.substring(fullKey.indexOf(prefix) + prefix.length() + 1);
    }

    public static Map<String, Map<String, String>> getGroupedCmsResultsSubMap(Map<String, String> results, String prefix) {
        final Map<String, Map<String, String>> groupedMap = new HashMap<>(0);
        for (Map.Entry<String, String> entry : results.entrySet()) {
            final String[] keyParts = splitKey(entry.getKey());
            if (keyParts[0].equals(prefix)) {
                Map<String, String> existingValues = groupedMap.get(keyParts[1]);
                if (existingValues == null) {
                    existingValues = new HashMap<>(0);
                    existingValues.put(keyParts[2], entry.getValue());
                    groupedMap.put(keyParts[1], existingValues);
                } else {
                    existingValues.put(keyParts[2], entry.getValue());
                }
            }
        }

        return groupedMap;
    }

    private static String[] splitKey(final String key) {
        if (key.indexOf('/') == -1)
            return new String[]{key};
        final String part0 = key.substring(0, key.indexOf('/'));
        String rest = key.substring(key.indexOf(part0) + part0.length() + 1);

        if (rest.indexOf('/') == -1)
            return new String[]{part0};

        final String part1 = rest.substring(0, rest.indexOf('/'));
        rest = rest.substring(rest.indexOf(part1) + part1.length() + 1);

        return new String[]{part0, part1, rest};
    }
}
