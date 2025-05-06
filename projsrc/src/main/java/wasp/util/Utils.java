package wasp.util;

public final class Utils {
    public String toTitleCase(String input) {
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

    public boolean containsNumeric(String input) {
        return input.matches(".*[0-9].*") ? true : false;
    }
}
