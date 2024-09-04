package medium;

import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddresses {
    public List<String> restoreIpAddresses(String s) {
        int length = s.length();
        List<String> result = new ArrayList<>();
        solve(s, length, result);
        return result;
    }

    private void solve(String s, int length, List<String> result) {
        int minLength1 = Math.max(1, length - 9);
        for (int length1 = minLength1; length1 <= 3 && length1 <= length - 3; length1++) {
            if (!isValid(s, 0, length1))
                continue;
            int minLength2 = Math.max(1, length - length1 - 6);
            for (int length2 = minLength2; length2 <= 3 && length2 <= length - length1 - 2; length2++) {
                if (!isValid(s, length1, length2))
                    continue;
                int minLength3 = Math.max(1, length - length1 - length2 - 3);
                for (int length3 = minLength3; length3 <= 3 && length3 <= length - length1 - length2 - 1; length3++) {
                    if (!isValid(s, length1 + length2, length3))
                        continue;
                    int length4 = length - length1 - length2 - length3;
                    if (isValid(s, length1 + length2 + length3, length4)) {
                        result.add(
                                String.join(".",
                                        s.substring(0, length1),
                                        s.substring(length1, length1 + length2),
                                        s.substring(length1 + length2, length1 + length2 + length3),
                                        s.substring(length1 + length2 + length3, length1 + length2 + length3 + length4)));
                    }
                }
            }

        }
    }

    private boolean isValid(String s, int start, int length) {
        if (length == 1)
            return true;
        if (s.charAt(start) != '0' && (length < 3 || s.substring(start, start + length).compareTo("255") <= 0))
            return true;
        return false;
    }

    public static void main(String[] args) {
        RestoreIPAddresses R = new RestoreIPAddresses();
        System.out.println(R.restoreIpAddresses("25525511135"));
        System.out.println(R.restoreIpAddresses("0000"));
        System.out.println(R.restoreIpAddresses("101023"));
    }
}
