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
