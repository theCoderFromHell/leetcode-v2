package medium;

import java.util.HashMap;

public class FlipGameII {
    public boolean canWin(String currentState) {
        int size = currentState.length();
        HashMap<String, Boolean> dp = new HashMap<>();
        return definiteWin(0, new StringBuffer(currentState), size, dp);
    }

    private boolean definiteWin(int player, StringBuffer currentState, int size, HashMap<String, Boolean> dp) {
        String s = new String(currentState);
        if (dp.containsKey(s))
            return dp.get(s);
        for (int i = 0; i < size; i++) {
            if (i+1 < size && currentState.charAt(i) == '+' && currentState.charAt(i+1) == '+') {
                StringBuffer copy = new StringBuffer(currentState);
                copy.setCharAt(i, '-');
                copy.setCharAt(i+1, '-');
                if (!definiteWin(1 - player, copy, size, dp)) {
                    dp.put(s, true);
                    return true;
                }
            }
        }
        dp.put(s, false);
        return false;
    }

    public static void main(String[] args) {
        FlipGameII F = new FlipGameII();
        System.out.println(F.canWin("++++"));
        System.out.println(F.canWin("+"));
        System.out.println(F.canWin("+--++++++"));
        System.out.println(F.canWin(""));
    }
}
