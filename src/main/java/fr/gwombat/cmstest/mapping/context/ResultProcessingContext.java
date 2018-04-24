package fr.gwombat.cmstest.mapping.context;

import fr.gwombat.cmstest.core.context.DynamicContext;

import java.lang.reflect.ParameterizedType;
import java.time.format.DateTimeFormatter;

/**
 * Created by guillaume.
 *
 * @since 21/04/2018
 */
public class ResultProcessingContext {

    private Class<?>          objectType;
    private ParameterizedType parameterizedType;
    private DateTimeFormatter dateTimeFormatter;
    private String            path;
    private DynamicContext    dynamicContext;

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

    public DynamicContext getDynamicContext() {
        return dynamicContext;
    }

    public void setDynamicContext(DynamicContext dynamicContext) {
        this.dynamicContext = dynamicContext;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public String toString() {
        return "ResultProcessingContext{" +
                "objectType=" + objectType +
                ", parameterizedType=" + parameterizedType +
                ", path='" + path + '\'' +
                '}';
    }
}
