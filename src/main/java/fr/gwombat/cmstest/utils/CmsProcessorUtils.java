package fr.gwombat.cmstest.utils;

import fr.gwombat.cmstest.annotations.CmsElement;
import fr.gwombat.cmstest.annotations.CmsNode;
import fr.gwombat.cmstest.annotations.CmsPageResult;
import fr.gwombat.cmstest.annotations.CmsProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsProcessorUtils.class);

    public static final String CMS_LANGUAGE = "fr";
    public static final String CMS_REPO     = "repo";

    private CmsProcessorUtils() {
    }

    public static String detectPropertyName(final Field field) {
        final CmsProperty annotation = field.getAnnotation(CmsProperty.class);
        if (annotation != null && !annotation.name().equals(""))
            return annotation.name();

        final CmsNode nodeAnnotation = field.getAnnotation(CmsNode.class);
        if (nodeAnnotation != null && !nodeAnnotation.name().equals(""))
            return nodeAnnotation.name();

        return toCamelCase(field.getName());
    }

    public static String detectPropertyName(final Method method) {
        final CmsProperty annotation = method.getAnnotation(CmsProperty.class);
        if (annotation != null && !annotation.name().equals(""))
            return annotation.name();

        final CmsNode nodeAnnotation = method.getAnnotation(CmsNode.class);
        if (nodeAnnotation != null && !nodeAnnotation.name().equals(""))
            return nodeAnnotation.name();

        return null;
    }

    public static String detectRootNodeName(final Class<?> resultType) {
        final CmsPageResult annotationCmsPageResult = resultType.getAnnotation(CmsPageResult.class);
        if (annotationCmsPageResult != null && !annotationCmsPageResult.rootNode().equals(""))
            return annotationCmsPageResult.rootNode();

        final CmsElement annotationCmsElement = resultType.getAnnotation(CmsElement.class);
        if (annotationCmsElement != null && !annotationCmsElement.nodeName().equals(""))
            return annotationCmsElement.nodeName();

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
        if (cmsValue == null)
            return null;
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
        for (Map.Entry<String, String> entry : globalResults.entrySet()) {
            String key = normalizeCmsPath(entry.getKey());
            if (key.startsWith(prefix))
                results.put(extractFromKey(key, prefix), entry.getValue());
        }
        return results;
    }

    private static String normalizeCmsPath(final String path) {
        final String prefix = CMS_REPO + "/" + CMS_LANGUAGE + "/";
        if (!path.contains(prefix))
            return path;

        return path.substring(path.indexOf(prefix) + prefix.length());
    }

    private static String extractFromKey(final String fullKey, final String prefix) {
        if (!fullKey.contains(prefix))
            return fullKey;

        final String resultKey = fullKey.substring(fullKey.indexOf(prefix) + prefix.length());
        if (resultKey.startsWith("/"))
            return resultKey.substring(1);
        return resultKey;
    }

    public static Map<String, Map<String, String>> getGroupedCmsResultsSubMap(Map<String, String> results, String prefix) {
        LOGGER.debug("Extracting results sub map from prefix: {}", prefix);
        final Map<String, Map<String, String>> groupedMap = new HashMap<>(0);

        int i = 0;
        for (Map.Entry<String, String> entry : results.entrySet()) {
            final String entryKey = extractFromKey(entry.getKey(), prefix);
            final String[] keyParts = splitKey(entryKey);
            LOGGER.trace("entry: {} --> splitted: {}", entry.getKey(), keyParts);
            if (entry.getKey().startsWith(prefix)) {
                String key = keyParts[0];
                String value = null;

                if (keyParts.length > 1)
                    value = keyParts[1];
                else
                    value = String.valueOf(i);

                Map<String, String> existingValues = groupedMap.get(key);
                if (existingValues == null) {
                    existingValues = new HashMap<>(0);
                    groupedMap.put(key, existingValues);
                }

                existingValues.put(value, entry.getValue());
            }
            i++;
        }

        return groupedMap;
    }

    private static String[] splitKey(final String key) {
        if (key.indexOf('/') == -1)
            return new String[]{key};
        final String part0 = key.substring(0, key.indexOf('/'));
        String rest = key.substring(key.indexOf(part0) + part0.length() + 1);

        if (rest.indexOf('/') == -1)
            return new String[]{part0, rest};

        final String part1 = rest.substring(0, rest.indexOf('/'));
        rest = rest.substring(rest.indexOf(part1) + part1.length() + 1);

        return new String[]{part0, part1, rest};
    }
}
