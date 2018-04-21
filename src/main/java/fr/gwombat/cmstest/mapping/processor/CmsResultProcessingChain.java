package fr.gwombat.cmstest.mapping.processor;

import fr.gwombat.cmstest.exceptions.CmsMappingException;

import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 15/04/2018
 */
@FunctionalInterface
public interface CmsResultProcessingChain {
    Object process(final Map<String, String> cmsResults, final ResultProcessingContext context) throws CmsMappingException;
}
