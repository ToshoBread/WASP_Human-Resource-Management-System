package wasp.util;

import java.awt.Dimension;
import java.awt.Toolkit;

public final class Utils {

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static double getScreenWidth() {
        return screenSize.getWidth();
    }

    public static double getScreenHeight() {
        return screenSize.getHeight();
    }

    public static String toTitleCase(String input) {
        String[] words = input.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }

            if (word.length() > 1) {
                sb.append(word.substring(1).toLowerCase());
            }

            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public static boolean containsNumeric(String input) {
        return input.matches(".*[0-9].*") ? true : false;
    }
}
