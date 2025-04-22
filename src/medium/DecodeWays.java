package medium;

public class DecodeWays {
    public int numDecodings(String s) {
        if(s == null || s.isEmpty())
            return 0;
        if(s.charAt(0) == '0')
            return 0;
        int length = s.length();
        for (int i = 1; i < length; i++) {
            if (s.charAt(i) == '0' && s.charAt(i-1) != '1' && s.charAt(i-1) != '2')
                return 0;
        }
        int[] dp = new int[length+1];
        dp[0] = 1;
        for (int i = 1; i < length; i++) {
            int one, two;
            one = s.charAt(i) == '0' ? 0 : 1;
            if (s.charAt(i) == '0')
                two = 1;
            else {
                int value = Integer.parseInt(s.substring(i-1, i+1));
                two = (value >= 10 && value <= 26) ? 1 : 0;
            }
            dp[i] = one*dp[i-1] + two*(i-2>=0 ? dp[i-2] : 1);
        }
        return dp[length-1];
    }

    public static void main(String[] args) {
        DecodeWays D = new DecodeWays();
        System.out.println(D.numDecodings("30"));
        System.out.println(D.numDecodings("12"));
        System.out.println(D.numDecodings("226"));
        System.out.println(D.numDecodings("06"));
    }
}
