package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public class SimpleTypeProcessor implements CmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTypeProcessor.class);

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isSimpleType(clazz);
    }

    @Override
    public Object process(final Map<String, String> cmsResults, final ResultProcessingContext context) {
        LOGGER.debug("Processing property {}, of type {}", context.getPath(), context.getObjectType());
        LOGGER.debug("available results: {}", cmsResults);
        final String value = cmsResults.get(context.getPath());
        LOGGER.debug("Corresponding value is: {}", value);
        return TypeUtils.castValue(context.getObjectType(), value);
    }
}
