package easy;

public class LengthOfLastWord {
    public static int lengthOfLastWord(String s) {
        if (s == null || s.isBlank() || s.isEmpty())
            return 0;
        char[] chars = s.toCharArray();
        int size = chars.length;
        int idx = size-1;
        while (idx >=0 && chars[idx] == ' ')
            idx--;
        if (idx < 0)
            return 0;
        int end = idx;
        while (idx >=0 && chars[idx] != ' ')
            idx--;
        return (end-idx);
    }
    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("Hello World"));
        System.out.println(lengthOfLastWord("   fly me   to   the moon  "));
        System.out.println(lengthOfLastWord("luffy is still joyboy"));
        System.out.println(lengthOfLastWord("     why do we exist      "));
    }
}
