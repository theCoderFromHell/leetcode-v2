package medium;

import java.util.HashMap;

public class FindLongestSpecialSubstringThatOccursThriceI {
    public int maximumLength(String s) {
        int N = s.length();
        HashMap<String, Integer> count = new HashMap<>();
        for (int start = 0; start < N; start++) {
            for (int end = start; end < N; end++) {
                if (s.charAt(end) != s.charAt(start))
                    break;
                String sub = s.substring(start, end + 1);
                count.put(sub, count.getOrDefault(sub, 0) + 1);
            }
        }

        int maxLength = -1;
        for (String sub : count.keySet()) {
            int currentCount = count.get(sub);
            if (currentCount >= 3 && sub.length() > maxLength)
                maxLength = sub.length();
        }
        return maxLength;
    }

    public static void main(String[] args) {
        FindLongestSpecialSubstringThatOccursThriceI F = new FindLongestSpecialSubstringThatOccursThriceI();
        System.out.println(F.maximumLength("cccerrrecdcdccedecdcccddeeeddcdcddedccdceeedccecde"));
        System.out.println(F.maximumLength("abcdef"));
        System.out.println(F.maximumLength("abcaba"));
    }
}
