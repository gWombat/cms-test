package fr.gwombat.cmstest.mapping.converters;

/**
 * Created by guillaume.
 *
 * @since 16/04/2018
 */
@FunctionalInterface
public interface PostConverter<T> {

    void postConvert(T targetObject);

}
