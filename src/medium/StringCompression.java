package medium;

public class StringCompression {
    public int compress(char[] chars) {
        int N = chars.length;
        int start = 0;
        int end = 0;
        while (end < N) {
            char current = chars[end];
            int count = 1;
            end++;
            while (end < N && chars[end] == current ) {
                end++;
                count++;
            }
            chars[start++] = current;
            if (count > 1)  {
                char[] frequency = String.valueOf(count).toCharArray();
                int length = frequency.length;
                for (int i = 0; i < length; i++)
                    chars[start + i] = frequency[i];
                start += length;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        StringCompression S = new StringCompression();
//        System.out.println(S.compress(new char[]{'a','a','b','b','c','c','c'}));
//        System.out.println(S.compress(new char[]{'a'}));
        System.out.println(S.compress(new char[]{'a','b','b','b','b','b','b','b','b','b','b','b','b'}));
    }
}
