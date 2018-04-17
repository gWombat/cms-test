package fr.gwombat.cmstest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public final class TypeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeUtils.class);

    private TypeUtils() {
    }

    public static boolean isCollection(final Class<?> parameterType) {
        return Collection.class.isAssignableFrom(parameterType);
    }

    public static boolean isMap(final Class<?> parameterType) {
        return Map.class.isAssignableFrom(parameterType);
    }

    public static boolean isSimpleType(final Class<?> parameterType) {
        if (parameterType == Byte.class || parameterType == byte.class)
            return true;
        if (parameterType == Float.class || parameterType == float.class)
            return true;
        if (parameterType == Integer.class || parameterType == int.class)
            return true;
        if (parameterType == Double.class || parameterType == double.class)
            return true;
        if (parameterType == Long.class || parameterType == long.class)
            return true;
        if (parameterType == Boolean.class || parameterType == boolean.class)
            return true;
        if (parameterType == Short.class || parameterType == short.class)
            return true;
        if (parameterType == String.class)
            return true;
        if (parameterType == Character.class || parameterType == char.class)
            return true;
        return Enum.class.isAssignableFrom(parameterType);
    }

    public static Object castValue(final Class<?> parameterType, final String cmsValue) {
        if (cmsValue == null)
            return null;
        if (parameterType == Byte.class || parameterType == byte.class)
            return Byte.parseByte(cmsValue);
        if (parameterType == Float.class || parameterType == float.class)
            return Float.parseFloat(cmsValue);
        if (parameterType == Integer.class || parameterType == int.class)
            return Integer.parseInt(cmsValue);
        if (parameterType == Double.class || parameterType == double.class)
            return Double.parseDouble(cmsValue);
        if (parameterType == Long.class || parameterType == long.class)
            return Long.parseLong(cmsValue);
        if (parameterType == Boolean.class || parameterType == boolean.class)
            return Boolean.parseBoolean(cmsValue);
        if (parameterType == Short.class || parameterType == short.class)
            return Short.parseShort(cmsValue);
        if (Enum.class.isAssignableFrom(parameterType))
            return Enum.valueOf((Class<Enum>) parameterType, cmsValue);

        return parameterType.cast(cmsValue);
    }

    public static List<Method> getAllMethods(final Class<?> clazz) {
        LOGGER.debug("Looking for all methods of class {}", clazz);
        List<Method> methods = new ArrayList<>(0);
        methods = addMethodsRecursively(methods, clazz);
        LOGGER.debug("{} methods found!", methods.size());
        return methods;
    }

    private static List<Method> addMethodsRecursively(final List<Method> methods, final Class<?> clazz) {
        LOGGER.debug("Adding all methods of class {}", clazz);
        methods.addAll(Arrays.asList(clazz.getMethods()));
        while (clazz.getSuperclass() != Object.class)
            addMethodsRecursively(methods, clazz.getSuperclass());
        return methods;
    }

    public static List<Field> getAllFields(final Class<?> clazz) {
        LOGGER.debug("Looking for all fields of class {}", clazz);
        List<Field> fields = new ArrayList<>(0);
        fields = addFieldsRecursively(fields, clazz);
        LOGGER.debug("{} fields found!", fields.size());
        return fields;
    }

    private static List<Field> addFieldsRecursively(final List<Field> fields, final Class<?> clazz) {
        LOGGER.debug("Adding all fields of class {}", clazz);
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        while (clazz.getSuperclass() != Object.class)
            addFieldsRecursively(fields, clazz.getSuperclass());
        return fields;
    }
}
