package medium;

public class RemoveAllOccurrencesOfASubstring {
    public String removeOccurrences(String s, String part) {
        int size = s.length();
        int partSize = part.length();
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < size) {
            sb.append(s.charAt(index++));
            if (!sb.isEmpty() && sb.length() >= partSize && sb.substring(sb.length()-partSize, sb.length()).equals(part)) {
                sb = new StringBuilder(sb.substring(0, sb.length()-partSize));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        RemoveAllOccurrencesOfASubstring R = new RemoveAllOccurrencesOfASubstring();
        System.out.println(R.removeOccurrences("daabcbaabcbc", "abc"));
        System.out.println(R.removeOccurrences("axxxxyyyyb", "xy"));
    }
}
