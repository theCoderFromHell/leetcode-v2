package medium;

import java.util.Arrays;

public class MatchsticksToSquare {
    public boolean makesquare(int[] matchsticks) {
        int size = matchsticks.length;
        int sum = 0, longest = 0;
        for (int i = 0; i < size; i++) {
            sum += matchsticks[i];
            longest = Math.max(longest, matchsticks[i]);
        }
        if (sum % 4 != 0)
            return false;
        int target = sum/4;
        if (longest > target)
            return false;
        boolean[] taken = new boolean[size];
        Arrays.sort(matchsticks);
        return match(matchsticks, size, taken, 0, 0, 0, target);
    }

    private boolean match(int[] matchsticks, int size, boolean[] taken, int sides, int index, int currSum, int target) {
        for (int i = index; i < size; i++) {
            if (taken[i])
                continue;
            if (currSum + matchsticks[i] > target)
                return false;
            currSum += matchsticks[i];
            taken[i] = true;
            if (currSum == target) {
                sides++;
                if (sides == 4)
                    return true;
                boolean found = match(matchsticks, size, taken, sides, 0, 0, target);
                if (found)
                    return true;
                else
                    sides--;
            } else if (currSum < target) {
                boolean found = match(matchsticks, size, taken, sides, i + 1, currSum, target);
                if (found)
                    return true;
            }
            currSum -= matchsticks[i];
            taken[i] = false;
        }
        return false;
    }

    public static void main(String[] args) {
        MatchsticksToSquare M =  new MatchsticksToSquare();
        System.out.println(M.makesquare(new int[]{1,1,1,2,2,2,3,4}));
        System.out.println(M.makesquare(new int[]{1,1,2,2,2}));
        System.out.println(M.makesquare(new int[]{3,3,3,3,4}));
        System.out.println(M.makesquare(new int[]{ 5,5,5,5,4,4,4,4,3,3,3,3}));
        System.out.println(M.makesquare(new int[]{6,6,6,6,4,2,2}));
    }
}
