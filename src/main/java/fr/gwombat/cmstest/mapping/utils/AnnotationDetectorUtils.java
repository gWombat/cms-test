package fr.gwombat.cmstest.mapping.utils;

import fr.gwombat.cmstest.core.context.DynamicContext;
import fr.gwombat.cmstest.exceptions.CmsMappingException;
import fr.gwombat.cmstest.mapping.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;

public final class AnnotationDetectorUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationDetectorUtils.class);

    private static final String EMPTY_STRING = "";

    private AnnotationDetectorUtils() {
    }

    public static DateTimeFormatter getDateTimeFormatter(final Field field) {
        final CmsTemporal cmsTemporalAnnotation = field.getAnnotation(CmsTemporal.class);
        if (cmsTemporalAnnotation == null || "".equals(cmsTemporalAnnotation.format()))
            return null;

        final String dateFormat = cmsTemporalAnnotation.format();
        return DateTimeFormatter.ofPattern(dateFormat);
    }

    public static DateTimeFormatter getDateTimeFormatter(final Method method) {
        final CmsTemporal cmsTemporalAnnotation = method.getAnnotation(CmsTemporal.class);
        if (cmsTemporalAnnotation == null || "".equals(cmsTemporalAnnotation.format()))
            return null;

        final String dateFormat = cmsTemporalAnnotation.format();
        return DateTimeFormatter.ofPattern(dateFormat);
    }

    public static String detectPropertyName(final Field field) {
        final CmsProperty annotation = field.getAnnotation(CmsProperty.class);
        if (annotation != null && !annotation.name().equals(EMPTY_STRING))
            return annotation.name();

        final CmsNode nodeAnnotation = field.getAnnotation(CmsNode.class);
        if (nodeAnnotation != null && !nodeAnnotation.name().equals(EMPTY_STRING))
            return nodeAnnotation.name();

        return WordUtils.toCamelCase(field.getName());
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

    public static String detectRootNodeName(final Class<?> clazz, DynamicContext dynamicContext) throws CmsMappingException {
        String rootNodeName = detectRootNodeNameInHierarchy(clazz, dynamicContext);
        if (rootNodeName != null) {
            LOGGER.debug("Custom node name found for clazz {}! Root node name={}", clazz, rootNodeName);
            return rootNodeName;
        }

        rootNodeName = WordUtils.toCamelCase(clazz.getSimpleName());
        LOGGER.debug("No custom node name found for class {}, applying default configuration. Root node name={}", clazz, rootNodeName);
        return rootNodeName;
    }

    private static String detectRootNodeNameInHierarchy(final Class<?> clazz, DynamicContext dynamicContext) throws CmsMappingException {
        if (clazz == Object.class)
            return null;

        LOGGER.debug("Detecting root node name on clazz {}...", clazz);
        final String dynamicNodeName = detectDynamicNodeName(clazz, dynamicContext);
        if (dynamicNodeName != null)
            return dynamicNodeName;

        LOGGER.debug("Looking for annotation {} in class {}", CmsElement.class, clazz);
        if (clazz.getAnnotation(CmsElement.class) != null && !clazz.getAnnotation(CmsElement.class).nodeName().equals(EMPTY_STRING))
            return clazz.getAnnotation(CmsElement.class).nodeName();

        return detectRootNodeNameInHierarchy(clazz.getSuperclass(), dynamicContext);
    }

    private static String detectDynamicNodeName(final Class<?> clazz, DynamicContext dynamicContext) throws CmsMappingException {
        LOGGER.debug("Looking for annotation {} in class {}", DynamicNodeName.class, clazz);
        if (clazz.getAnnotation(DynamicNodeName.class) != null) {
            if (dynamicContext == null)
                throw new CmsMappingException("The class " + clazz + " is annotated with @" + DynamicNodeName.class.getSimpleName() + " but no class " + DynamicContext.class.getName() + " is provided to find dynamic value. Please add context.");
            final DynamicNodeName dynamicNodeNameAnnotation = clazz.getAnnotation(DynamicNodeName.class);
            if (!EMPTY_STRING.equals(dynamicNodeNameAnnotation.key()))
                return dynamicContext.getDynamicNodeName(dynamicNodeNameAnnotation.key());
            return dynamicContext.getDynamicNodeName(clazz);
        }
        return null;
    }
}
