package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.mapping.converters.Converter;
import fr.gwombat.cmstest.mapping.converters.PostConverter;

public interface ConverterRegistry {

    ConverterRegistry addConverter(Converter converter);

    ConverterRegistry addPostConverter(PostConverter postConverter);

}
