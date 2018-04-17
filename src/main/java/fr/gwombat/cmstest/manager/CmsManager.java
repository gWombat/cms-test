package fr.gwombat.cmstest.manager;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@FunctionalInterface
public interface CmsManager {

    <T> T produceCmsPageResult(Class<T> resultType);
}
