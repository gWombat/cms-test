package fr.gwombat.cmstest.configuration;

import fr.gwombat.cmstest.registry.ConverterRegistry;

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

    default String getPropertySeparator() {
        return "/";
    }

    default String getRootNodePrefix() {
        return null;
    }

}
