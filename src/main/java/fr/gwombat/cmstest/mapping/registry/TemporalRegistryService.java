package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.utils.TemporalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class TemporalRegistryService implements TemporalRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemporalRegistryService.class);

    private final List<DateTimeFormatter> formatters;


    public TemporalRegistryService() {
        formatters = new ArrayList<>();
    }

    @Override
    public TemporalRegistryService addDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {

        if (dateTimeFormatter != null)
            formatters.add(dateTimeFormatter);
        return this;
    }

    public Temporal parse(final Class<? extends Temporal> clazz, final String formattedTemporal, DateTimeFormatter dateTimeFormatter) throws CmsMappingException {
        try {
            return TemporalUtils.cast(clazz, dateTimeFormatter, formattedTemporal);
        } catch (DateTimeParseException e) {
            throw new CmsMappingException(e);
        }
    }

    public Temporal parse(final Class<? extends Temporal> clazz, final String formattedTemporal) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return TemporalUtils.cast(clazz, formatter, formattedTemporal);
            } catch (DateTimeParseException e) {
                LOGGER.info("Enable to parse {}: {}", formattedTemporal, e.getMessage());
            }
        }
        return null;
    }
}
