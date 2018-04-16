package fr.gwombat.cmstest.processor;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsProcessor {

    boolean isExecutable(Class<?> clazz);

    Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName);
}
