package fr.gwombat.cmstest.processor;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
@FunctionalInterface
public interface CmsResultProcessingChain {
    Object process(Class<?> clazz, Map<String, String> cmsResults, ParameterizedType parameterizedType, String rootName);
}
