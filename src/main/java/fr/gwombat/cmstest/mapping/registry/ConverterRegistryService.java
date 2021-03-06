package fr.gwombat.cmstest.mapping.registry;

import fr.gwombat.cmstest.mapping.converters.Converter;
import fr.gwombat.cmstest.mapping.converters.PostConverter;

import java.util.ArrayList;
import java.util.List;

public class ConverterRegistryService implements ConverterRegistry {

    private final List<Converter>     converters;
    private final List<PostConverter> postConverters;

    public ConverterRegistryService() {
        converters = new ArrayList<>(0);
        postConverters = new ArrayList<>(0);
    }

    @Override
    public ConverterRegistry addConverter(Converter converter) {
        converters.add(converter);
        return this;
    }

    @Override
    public ConverterRegistry addPostConverter(PostConverter postConverter) {
        postConverters.add(postConverter);
        return this;
    }

    public <T> Converter<T> getConverter(final Class<T> clazz) {
        return converters
                .stream()
                .filter(converter -> converter.getClass() == clazz)
                .findFirst()
                .orElse(null);
    }

    public <T> PostConverter<T> getPostConverter(final Class<T> clazz) {
        return postConverters
                .stream()
                .filter(postConverter -> postConverter.getClass() == clazz)
                .findFirst()
                .orElse(null);
    }
}
