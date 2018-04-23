package fr.gwombat.cmstest.configuration;

import fr.gwombat.cmstest.mapping.registry.CallConfigurerRegistry;
import fr.gwombat.cmstest.mapping.registry.ConverterRegistry;
import fr.gwombat.cmstest.mapping.registry.TemporalRegistry;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsConfigurer {

    default void addConverters(ConverterRegistry converterRegistry) {
    }

    default void addDateTimeFormatters(TemporalRegistry temporalRegistry) {

    }

    default void addCallConfigurers(CallConfigurerRegistry callConfigurerRegistry) {
    }

    default String getPropertySeparator() {
        return "/";
    }

    default String getRootNodePrefix() {
        return null;
    }

}
