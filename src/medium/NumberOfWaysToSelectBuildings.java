package medium;

public class NumberOfWaysToSelectBuildings {
    public long numberOfWays(String s) {
        int N = s.length();
        int[] leftOnes = new int[N];
        int[] rightOnes = new int[N];
        int[] leftZeros = new int[N];
        int[] rightZeros = new int[N];
        int ones = 0, zeros = 0;
        for (int i = 0; i < N; i++) {
            if (s.charAt(i) == '0') {
                zeros++;
                leftOnes[i] = ones;
            } else {
                ones++;
                leftZeros[i] = zeros;
            }
        }
        ones = 0;
        zeros = 0;
        for (int i = N-1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                zeros++;
                rightOnes[i] = ones;
            } else {
                ones++;
                rightZeros[i] = zeros;
            }
        }
        long result = 0;
        for (int i = 0; i < N; i++) {
            if (s.charAt(i) == '0')
                result += (leftOnes[i] * rightOnes[i]);
            else
                result += (leftZeros[i] * rightZeros[i]);
        }
        return result;
    }
}
