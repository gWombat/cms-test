package fr.gwombat.cmstest.mapping.registry;

import java.time.format.DateTimeFormatter;

public interface TemporalRegistry {

    TemporalRegistry addDateTimeFormatter(DateTimeFormatter dateTimeFormatter);
}
