package medium;

public class CountAndSay {
    public static String countAndSay(int n) {
        if (n == 1)
            return "1";
        String text = "1";
        while (n > 1) {
            StringBuilder builder = new StringBuilder();
            int length = text.length();
            int idx = 0;
            while (idx < length) {
                int digit = text.charAt(idx) - '0';
                int count = 1;
                while (idx+1 < length && text.charAt(idx) == text.charAt(idx+1)) {
                    count++;
                    idx++;
                }
                builder.append(count).append(digit);
                idx++;
            }
            text = builder.toString();
            n--;
        }
        return text;
    }

    public static void main(String[] args) {
        System.out.println(countAndSay(30));
    }
}
