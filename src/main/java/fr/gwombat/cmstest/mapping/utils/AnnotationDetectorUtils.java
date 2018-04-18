package fr.gwombat.cmstest.mapping.utils;

import fr.gwombat.cmstest.mapping.annotations.CmsElement;
import fr.gwombat.cmstest.mapping.annotations.CmsNode;
import fr.gwombat.cmstest.mapping.annotations.CmsPageResult;
import fr.gwombat.cmstest.mapping.annotations.CmsProperty;
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

    public static String detectRootNodeName(final Class<?> clazz) {
        LOGGER.debug("Looking for annotation {} in {} hierarchy...", CmsPageResult.class, clazz);
        final CmsPageResult annotationCmsPageResult = findCmsPageResultAnnotationRecursively(clazz);
        if (annotationCmsPageResult != null && !annotationCmsPageResult.rootNode().equals(EMPTY_STRING))
            return annotationCmsPageResult.rootNode();

        LOGGER.debug("Looking for annotation {} in {} hierarchy...", CmsElement.class, clazz);
        final CmsElement annotationCmsElement = findCmsElementAnnotationRecursively(clazz);
        if (annotationCmsElement != null && !annotationCmsElement.nodeName().equals(EMPTY_STRING))
            return annotationCmsElement.nodeName();

        return toCamelCase(clazz.getSimpleName());
    }

    private static CmsElement findCmsElementAnnotationRecursively(final Class<?> clazz) {
        if (clazz == Object.class)
            return null;
        LOGGER.debug("Looking for annotation {} in class {}", CmsElement.class, clazz);
        if (clazz.getAnnotation(CmsElement.class) != null)
            return clazz.getAnnotation(CmsElement.class);
        return findCmsElementAnnotationRecursively(clazz.getSuperclass());
    }

    private static CmsPageResult findCmsPageResultAnnotationRecursively(final Class<?> clazz) {
        if (clazz == Object.class)
            return null;
        LOGGER.debug("Looking for annotation {} in class {}", CmsPageResult.class, clazz);
        if (clazz.getAnnotation(CmsPageResult.class) != null)
            return clazz.getAnnotation(CmsPageResult.class);
        return findCmsPageResultAnnotationRecursively(clazz.getSuperclass());
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
