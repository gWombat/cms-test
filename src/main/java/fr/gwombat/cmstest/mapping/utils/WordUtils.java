package fr.gwombat.cmstest.mapping.utils;

public final class WordUtils {

    private WordUtils() {
    }

    public static String capitalize(String word) {
        if (word == null || "".equals(word))
            return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public static String toCamelCase(String value) {
        if (value == null || "".equals(value))
            return value;
        return Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }
}
