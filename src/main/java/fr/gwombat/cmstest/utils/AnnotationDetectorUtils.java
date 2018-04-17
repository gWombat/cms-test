package fr.gwombat.cmstest.utils;

import fr.gwombat.cmstest.annotations.CmsElement;
import fr.gwombat.cmstest.annotations.CmsNode;
import fr.gwombat.cmstest.annotations.CmsPageResult;
import fr.gwombat.cmstest.annotations.CmsProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class AnnotationDetectorUtils {

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

    public static String detectRootNodeName(final Class<?> resultType) {
        final CmsPageResult annotationCmsPageResult = resultType.getAnnotation(CmsPageResult.class);
        if (annotationCmsPageResult != null && !annotationCmsPageResult.rootNode().equals(EMPTY_STRING))
            return annotationCmsPageResult.rootNode();

        final CmsElement annotationCmsElement = resultType.getAnnotation(CmsElement.class);
        if (annotationCmsElement != null && !annotationCmsElement.nodeName().equals(EMPTY_STRING))
            return annotationCmsElement.nodeName();

        return toCamelCase(resultType.getSimpleName());
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
