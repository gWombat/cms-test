package fr.gwombat.cmstest.core.context;

import java.util.HashMap;
import java.util.Map;

public class DynamicContext {

    private final Map<String, String> dynamicNodeNames;
    private final Map<String, String> dynamicPathVariables;

    public DynamicContext() {
        this.dynamicNodeNames = new HashMap<>(0);
        this.dynamicPathVariables = new HashMap<>(0);
    }

    public DynamicContext addDynamicPathVariable(final String key, final String value){
        this.dynamicPathVariables.put(key, value);
        return this;
    }

    public DynamicContext addDynamicNodeName(Class<?> clazz, String nodeName) {
        return addDynamicNodeName(clazz.getName(), nodeName);
    }

    public DynamicContext addDynamicNodeName(String key, String nodeName) {
        this.dynamicNodeNames.put(key, nodeName);
        return this;
    }

    public String getDynamicPathVariable(String key) {
        return dynamicPathVariables.get(key);
    }

    public String getDynamicNodeName(String key) {
        return dynamicNodeNames.get(key);
    }

    public String getDynamicNodeName(Class<?> key) {
        return dynamicNodeNames.get(key.getName());
    }

    public Map<String, String> getDynamicNodeNames() {
        return dynamicNodeNames;
    }

    public Map<String, String> getDynamicPathVariables() {
        return dynamicPathVariables;
    }

    @Override
    public String toString() {
        return "DynamicContext{" +
                "dynamicNodeNames=" + dynamicNodeNames +
                ", dynamicPathVariables=" + dynamicPathVariables +
                '}';
    }
}
