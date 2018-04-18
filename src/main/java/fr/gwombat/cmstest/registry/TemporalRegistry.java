package fr.gwombat.cmstest.registry;

import java.time.format.DateTimeFormatter;

public interface TemporalRegistry {

    TemporalRegistry addDateTimeFormatter(DateTimeFormatter dateTimeFormatter);
}
