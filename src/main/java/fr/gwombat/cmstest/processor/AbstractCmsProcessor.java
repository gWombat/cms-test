package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.context.CmsResultContextFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public abstract class AbstractCmsProcessor implements CmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCmsProcessor.class);

    protected final CmsResultContextFacade   cmsResultContextFacade;
    protected final CmsResultProcessingChain cmsResultProcessingChain;

    public AbstractCmsProcessor(CmsResultContextFacade cmsResultContextFacade, CmsResultProcessingChain cmsResultProcessingChain) {
        this.cmsResultProcessingChain = cmsResultProcessingChain;
        this.cmsResultContextFacade = cmsResultContextFacade;
    }

    protected Map<String, String> getCmsResultsSubMap(final Map<String, String> globalResults, final String prefix) {
        LOGGER.debug("Extracting results from prefix: {}", prefix);
        return globalResults.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .collect(Collectors.toMap(
                        entry -> removePrefixFromKey(entry.getKey(), prefix),
                        Map.Entry::getValue)
                );
    }

    protected Map<String, Map<String, String>> getGroupedCmsResultsSubMap(Map<String, String> results, String prefix) {
        LOGGER.debug("Extracting results sub map from prefix: {}", prefix);
        final Map<String, Map<String, String>> groupedMap = new HashMap<>(0);

        results.entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .forEach(entry -> {
                    final String entryKey = removePrefixFromKey(entry.getKey(), prefix);
                    final String[] keyParts = entryKey.split(String.valueOf(cmsResultContextFacade.getPropertySeparator()), 2);

                    final String key = keyParts[0];
                    final String value = keyParts.length > 1 ? keyParts[1] : key;
                    groupedMap.computeIfAbsent(key, newMap -> new HashMap<>()).put(value, entry.getValue());
                });

        return groupedMap;
    }

    private static String removePrefixFromKey(final String fullKey, final String prefix) {
        if (!fullKey.contains(prefix))
            return fullKey;
        return fullKey.substring(fullKey.indexOf(prefix) + prefix.length() + 1);
    }
}
