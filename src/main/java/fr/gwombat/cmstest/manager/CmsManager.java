package fr.gwombat.cmstest.manager;

import java.util.List;
import java.util.Map;

/**
 * Created by guillaume.
 *
 * @since 14/04/2018
 */
public interface CmsManager {

    <T> T produceComplexObject(Class<T> resultType);

    <T> T produceSimpleObject(Class<T> resultType, String propertyName);

//    <T> List<T> produceList(Class<T> listType, String cmsPropertyName);
//
//    <T> Map<String, T> produceMap(Class<T> valueType, String cmsPropertyName);
}
