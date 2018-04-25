package fr.gwombat.cmstest.core.manager;

import fr.gwombat.cmstest.core.CmsCallConfigWrapper;
import fr.gwombat.cmstest.core.context.DynamicContext;
import fr.gwombat.cmstest.core.path.CmsPath;

import java.util.List;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsManager {

    List<CmsPath> createCmsCallsTemporary(CmsCallConfigWrapper callWrapper, DynamicContext dynamicContext);

    <T> T produceComplexObject(CmsCallConfigWrapper callWrapper, Class<T> resultType, DynamicContext dynamicContext);

    <T> T produceComplexObject(CmsCallConfigWrapper callWrapper, Class<T> resultType);

//    <T> T produceSimpleObject(Class<T> resultType, String propertyName);

//    <T> List<T> produceList(Class<T> listType, String cmsPropertyName);
//
//    <T> Map<String, T> produceMap(Class<T> valueType, String cmsPropertyName);
}
