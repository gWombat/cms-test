package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.mapping.context.CmsContextFacade;
import fr.gwombat.cmstest.mapping.utils.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.time.temporal.Temporal;
import java.util.Map;

public class TemporalProcessor extends AbstractCmsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemporalProcessor.class);

    public TemporalProcessor(CmsContextFacade cmsContextFacade) {
        super(cmsContextFacade);
    }

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return TypeUtils.isTemporal(clazz);
    }

    @Override
    public Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName) {
        LOGGER.debug("Processing property {}, of type {}", rootName, clazz);
        LOGGER.debug("available results: {}", cmsResults);
        final String value = cmsResults.get(rootName);
        LOGGER.debug("Corresponding value is: {}", value);
        return cmsContextFacade.parseDate((Class<? extends Temporal>) clazz, value);
    }
}
