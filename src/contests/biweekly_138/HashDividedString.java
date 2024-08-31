package contests.biweekly_138;

public class HashDividedString {
    public String stringHash(String s, int k) {
        int N = s.length();
        StringBuilder resultSb = new StringBuilder();
        int start = 0;
        int end = k;

        while (end <= N) {
            String currentSub = s.substring(start, end);
            int sum = 0;
            for (int i = 0; i < k; i++) {
                sum += (currentSub.charAt(i) - 'a');
            }
            sum = sum % 26;
            resultSb.append((char) (sum + 'a'));
            start += k;
            end += k;
        }
        return resultSb.toString();
    }

    public static void main(String[] args) {
        HashDividedString H = new HashDividedString();
        System.out.println(H.stringHash("abcd", 2));
    }
}
