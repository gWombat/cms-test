package fr.gwombat.cmstest.converters;

/**
 * Created by guillaume.
 *
 * @since 16/04/2018
 */
public interface PostConverter<T> {

    void postConvert(T targetObject);

}
