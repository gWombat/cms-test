package fr.gwombat.cmstest.mapping.processor;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
@FunctionalInterface
public interface CmsProcessor {

    default boolean isExecutable(Class<?> clazz) {
        return true;
    }

    Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName);
}
