package medium;

public class ReverseWordsInAString {

    public static String reverseWords(String s) {
        if (s == null || s.isEmpty() || s.isBlank())
            return s;
        int size = s.length();
        String result = null;
        int start = -1;
        int end = 0;
        while (end < size) {
            while (end < size && s.charAt(end) == ' ')
                end++;
            if (end < size && s.charAt(end) != ' ')
                start = end;
            while (end < size && s.charAt(end) != ' ')
                end++;
            if (start != -1) {
                String temp = s.substring(start, end);
                if (result ==  null)
                    result = temp;
                else
                    result = temp + " " + result;
                start = -1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("the sky is blue"));
        System.out.println(reverseWords("  hello world  "));
        System.out.println(reverseWords("a good   example"));
    }
}
