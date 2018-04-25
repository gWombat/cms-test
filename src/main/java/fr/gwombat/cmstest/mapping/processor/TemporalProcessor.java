package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.configuration.CmsConfigurer;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.context.ResultProcessingContext;
import fr.gwombat.cmstest.mapping.registry.TemporalRegistryService;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.temporal.Temporal;
import java.util.Map;

public class TemporalProcessor extends AbstractCmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemporalProcessor.class);

    private final TemporalRegistryService temporalRegistryService;

    public TemporalProcessor(CmsConfigurer cmsConfigurer, TemporalRegistryService temporalRegistryService) {
        super(cmsConfigurer);
        this.temporalRegistryService = temporalRegistryService;
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isTemporal(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object process(Map<String, String> cmsResults, final ResultProcessingContext context) throws CmsMappingException {
        LOGGER.debug("Processing property {}, of type {}", context.getPath(), context.getObjectType());
        LOGGER.debug("available results: {}", cmsResults);
        final String value = cmsResults.get(context.getPath());
        LOGGER.debug("Corresponding value is: {}", value);

        if (context.getDateTimeFormatter() != null)
            return temporalRegistryService.parse((Class<? extends Temporal>) context.getObjectType(), value, context.getDateTimeFormatter());

        return temporalRegistryService.parse((Class<? extends Temporal>) context.getObjectType(), value);
    }
}
