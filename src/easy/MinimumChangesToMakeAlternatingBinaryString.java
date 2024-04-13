package easy;

public class MinimumChangesToMakeAlternatingBinaryString {
    public int minOperations(String s) {
        int N = s.length();
        int modifications = 0;
        for (int i = 0; i < N; i++) {
            if (i % 2 == 0 && s.charAt(i) != '0')
                modifications++;
            if (i % 2 == 1 && s.charAt(i) != '1')
                modifications++;
        }
        return Math.min(modifications, N - modifications);
    }
}
