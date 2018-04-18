package fr.gwombat.cmstest.configuration;

import fr.gwombat.cmstest.registry.ConverterRegistry;
import fr.gwombat.cmstest.registry.TemporalRegistry;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsResultConfigurer {

    default void addConverters(ConverterRegistry converterRegistry) {
    }

    default void addPostConverters(ConverterRegistry converterRegistry) {
    }

    default void addDateTimeFormatter(TemporalRegistry temporalRegistry) {

    }

    default String getPropertySeparator() {
        return "/";
    }

    default String getRootNodePrefix() {
        return null;
    }

}
