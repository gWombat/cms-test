package fr.gwombat.cmstest.mapping.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public final class TemporalUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemporalUtils.class);

    private TemporalUtils() {
    }


    public static Temporal cast(Class<? extends Temporal> clazz, DateTimeFormatter dateTimeFormatter, String value) {
        if (clazz == LocalDate.class)
            return LocalDate.parse(value, dateTimeFormatter);
        if (clazz == OffsetTime.class)
            return OffsetTime.parse(value, dateTimeFormatter);
        if (clazz == ZonedDateTime.class)
            return ZonedDateTime.parse(value, dateTimeFormatter);
        if (clazz == LocalDateTime.class)
            return LocalDateTime.parse(value, dateTimeFormatter);
        if (clazz == Instant.class)
            return Instant.parse(value);
        if (clazz == OffsetDateTime.class)
            return OffsetDateTime.parse(value, dateTimeFormatter);
        if (clazz == Year.class)
            return Year.parse(value, dateTimeFormatter);
        if (clazz == LocalTime.class)
            return LocalTime.parse(value, dateTimeFormatter);
        if (clazz == YearMonth.class)
            return YearMonth.parse(value, dateTimeFormatter);

        LOGGER.warn("{} cannot be parsed to {}. No parsing strategy available..", value, clazz);
        return null;
    }
}
