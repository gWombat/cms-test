package fr.gwombat.cmstest.mapping.processor;

import java.lang.reflect.ParameterizedType;

/**
 * Created by guillaume.
 *
 * @since 21/04/2018
 */
public class ResultProcessingContext {

    private Class<?>          objectType;
    private ParameterizedType parameterizedType;
    private String            path;

    public Class<?> getObjectType() {
        return objectType;
    }

    public void setObjectType(Class<?> objectType) {
        this.objectType = objectType;
    }

    public ParameterizedType getParameterizedType() {
        return parameterizedType;
    }

    public void setParameterizedType(ParameterizedType parameterizedType) {
        this.parameterizedType = parameterizedType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
