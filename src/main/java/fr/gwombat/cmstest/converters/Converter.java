package fr.gwombat.cmstest.converters;

import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@FunctionalInterface
public interface Converter<T> {

    T convert(Map<String, String> cmsResults);
}
