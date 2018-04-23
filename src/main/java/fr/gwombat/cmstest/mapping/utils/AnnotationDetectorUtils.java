package fr.gwombat.cmstest.mapping.utils;

import fr.gwombat.cmstest.core.context.DynamicNodesContext;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.annotations.CmsElement;
import fr.gwombat.cmstest.mapping.annotations.CmsNode;
import fr.gwombat.cmstest.mapping.annotations.CmsProperty;
import fr.gwombat.cmstest.mapping.annotations.DynamicNodeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class AnnotationDetectorUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationDetectorUtils.class);

    private static final String EMPTY_STRING = "";

    private AnnotationDetectorUtils() {
    }

    public static String detectPropertyName(final Field field) {
        final CmsProperty annotation = field.getAnnotation(CmsProperty.class);
        if (annotation != null && !annotation.name().equals(EMPTY_STRING))
            return annotation.name();

        final CmsNode nodeAnnotation = field.getAnnotation(CmsNode.class);
        if (nodeAnnotation != null && !nodeAnnotation.name().equals(EMPTY_STRING))
            return nodeAnnotation.name();

        return toCamelCase(field.getName());
    }

    public static String detectPropertyName(final Method method) {
        final CmsProperty annotation = method.getAnnotation(CmsProperty.class);
        if (annotation != null && !annotation.name().equals(EMPTY_STRING))
            return annotation.name();

        final CmsNode nodeAnnotation = method.getAnnotation(CmsNode.class);
        if (nodeAnnotation != null && !nodeAnnotation.name().equals(EMPTY_STRING))
            return nodeAnnotation.name();

        return null;
    }

    public static String detectRootNodeName(final Class<?> clazz, DynamicNodesContext dynamicNodesContext) throws CmsMappingException {
        String rootNodeName = detectRootNodeNameInHierarchy(clazz, dynamicNodesContext);
        if (rootNodeName != null) {
            LOGGER.debug("Custom node name found for clazz {}! Root node name={}", clazz, rootNodeName);
            return rootNodeName;
        }

        rootNodeName = toCamelCase(clazz.getSimpleName());
        LOGGER.debug("No custom node name found for class {}, applying default configuration. Root node name={}", clazz, rootNodeName);
        return rootNodeName;
    }

    private static String detectRootNodeNameInHierarchy(final Class<?> clazz, DynamicNodesContext dynamicNodesContext) throws CmsMappingException {
        if (clazz == Object.class)
            return null;

        LOGGER.debug("Detecting root node name on clazz {}...", clazz);
        final String dynamicNodeName = detectDynamicNodeName(clazz, dynamicNodesContext);
        if (dynamicNodeName != null)
            return dynamicNodeName;

        LOGGER.debug("Looking for annotation {} in class {}", CmsElement.class, clazz);
        if (clazz.getAnnotation(CmsElement.class) != null && !clazz.getAnnotation(CmsElement.class).nodeName().equals(EMPTY_STRING))
            return clazz.getAnnotation(CmsElement.class).nodeName();

        return detectRootNodeNameInHierarchy(clazz.getSuperclass(), dynamicNodesContext);
    }

    private static String detectDynamicNodeName(final Class<?> clazz, DynamicNodesContext dynamicNodesContext) throws CmsMappingException {
        LOGGER.debug("Looking for annotation {} in class {}", DynamicNodeName.class, clazz);
        if (clazz.getAnnotation(DynamicNodeName.class) != null) {
            if (dynamicNodesContext == null)
                throw new CmsMappingException("The class " + clazz + " is annotated with @" + DynamicNodeName.class.getSimpleName() + " but no class " + DynamicNodesContext.class.getName() + " is provided to find dynamic value. Please add context.");
            final DynamicNodeName dynamicNodeNameAnnotation = clazz.getAnnotation(DynamicNodeName.class);
            if (!EMPTY_STRING.equals(dynamicNodeNameAnnotation.key()))
                return dynamicNodesContext.getDynamicNodeName(dynamicNodeNameAnnotation.key());
            return dynamicNodesContext.getDynamicNodeName(clazz);
        }
        return null;
    }

    private static String toCamelCase(String value) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (i == 0)
                stringBuilder.append(Character.toLowerCase(c));
            else
                stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
