package fr.gwombat.cmstest.core;

import java.util.HashMap;
import java.util.Map;

public class DynamicNodesContext {

    private final Map<String, String> dynamicNodeNames;

    public DynamicNodesContext() {
        this.dynamicNodeNames = new HashMap<>(0);
    }

    public DynamicNodesContext addDynamicNodeName(Class<?> clazz, String nodeName) {
        return addDynamicNodeName(clazz.getName(), nodeName);
    }

    public DynamicNodesContext addDynamicNodeName(String key, String nodeName) {
        this.dynamicNodeNames.put(key, nodeName);
        return this;
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

    @Override
    public String toString() {
        return "DynamicNodesContext{" +
                "dynamicNodeNames=" + dynamicNodeNames +
                '}';
    }
}
