package fr.gwombat.cmstest.processor;

import fr.gwombat.cmstest.utils.CmsProcessorUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
public class SimpleTypeProcessor implements CmsProcessor {

    @Override
    public boolean isExecutable(Class<?> clazz) {
        return CmsProcessorUtils.isSimpleType(clazz);
    }

    @Override
    public Object process(Map<String, String> cmsResults, Class<?> clazz, ParameterizedType parameterizedType, String rootName) {
        final String value = cmsResults.get(rootName);
        return CmsProcessorUtils.castValue(clazz, value);
    }
}
