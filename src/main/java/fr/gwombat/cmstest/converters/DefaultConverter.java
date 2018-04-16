package fr.gwombat.cmstest.converters;

import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public class DefaultConverter implements Converter<Void> {
    @Override
    public Void convert(Map<String, String> cmsResults) {
        return null;
    }
}
