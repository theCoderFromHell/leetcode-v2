package medium;

public class MinimumTimeToMakeRopeColorful {
    public int minCost(String colors, int[] neededTime) {
        int size = colors.length();
        int index = 0;
        int result = 0;
        while (index < size) {
            int timeSum = neededTime[index];
            int maxTime = neededTime[index];
            while (index+1 < size && colors.charAt(index) == colors.charAt(index+1)) {
                timeSum += neededTime[index+1];
                maxTime = Math.max(maxTime, neededTime[index+1]);
                index++;
            }
            result += (timeSum - maxTime);
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
        MinimumTimeToMakeRopeColorful M = new MinimumTimeToMakeRopeColorful();
        System.out.println(M.minCost("abaac", new int[]{1,2,3,4,5}));
        System.out.println(M.minCost("abc", new int[]{1,2,3}));
        System.out.println(M.minCost("aabaa", new int[]{1,2,3,4,1}));
    }
}
