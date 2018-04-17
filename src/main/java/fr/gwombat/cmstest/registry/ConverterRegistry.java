package fr.gwombat.cmstest.registry;

import fr.gwombat.cmstest.converters.Converter;
import fr.gwombat.cmstest.converters.PostConverter;

public interface ConverterRegistry {

    ConverterRegistry addConverter(Converter converter);

    ConverterRegistry addPostConverter(PostConverter postConverter);

}
