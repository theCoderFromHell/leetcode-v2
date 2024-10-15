package medium;

public class SeparateBlackAndWhiteBallsDisconnected {
    public long minimumSteps(String s) {
        int N = s.length();
        int target = N-1;
        while (target >= 0 && s.charAt(target) == '1')
            target--;
        long result = 0;
        for (int i = target -1; i >= 0; i--) {
            if (s.charAt(i) == '1') {
                result += (target - i);
                target--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SeparateBlackAndWhiteBallsDisconnected S = new SeparateBlackAndWhiteBallsDisconnected();
        System.out.println(S.minimumSteps("101"));
        System.out.println(S.minimumSteps("100"));
        System.out.println(S.minimumSteps("0111"));
        System.out.println(S.minimumSteps("0011000010"));

    }
}
