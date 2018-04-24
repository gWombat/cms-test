package fr.gwombat.cmstest.mapping.utils;

public final class WordUtils {

    private WordUtils() {
    }

    public static String capitalize(String word) {
        if (word == null || "".equals(word))
            return word;

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toUpperCase(word.charAt(0)));
        stringBuilder.append(word.substring(1));

        return stringBuilder.toString();
    }

    public static String toCamelCase(String value) {
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
