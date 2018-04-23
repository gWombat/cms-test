package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.context.ResultProcessingContext;

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

    Object process(Map<String, String> cmsResults, ResultProcessingContext context) throws CmsMappingException;
}
