package fr.gwombat.cmstest.mapping.manager;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.DynamicNodesContext;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsManager {

    List<CmsPath> createCmsCallsTemporary(CmsCallConfigWrapper callWrapper, Long departureCityId);

    <T> T produceComplexObject(CmsCallConfigWrapper callWrapper, Class<T> resultType, DynamicNodesContext dynamicNodesContext);

    <T> T produceComplexObject(CmsCallConfigWrapper callWrapper, Class<T> resultType);

    <T> T produceSimpleObject(Class<T> resultType, String propertyName);

//    <T> List<T> produceList(Class<T> listType, String cmsPropertyName);
//
//    <T> Map<String, T> produceMap(Class<T> valueType, String cmsPropertyName);
}
