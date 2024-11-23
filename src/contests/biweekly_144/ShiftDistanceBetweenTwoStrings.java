package contests.biweekly_144;

public class ShiftDistanceBetweenTwoStrings {
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        int length = s.length();
        long totalCost = 0;
        for (int i = 0; i < length; i++) {
            int source = s.charAt(i) - 'a';
            int destination = t.charAt(i) - 'a';
            long prevTotalCost = calculate(source, destination, previousCost, nextCost, true);
            long nextTotalCost = calculate(source, destination, previousCost, nextCost, false);
            totalCost += Math.min(prevTotalCost, nextTotalCost);
        }
        return totalCost;
    }

    private long calculate(int source, int destination, int[] previousCost, int[] nextCost, boolean previous) {
        long total = 0;
        while (source != destination) {
            if (previous) {
                total += previousCost[source];
                source = (source == 0) ? 25 : source-1;
            } else {
                total += nextCost[source];
                source = (source == 25) ? 0 : source+1;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        ShiftDistanceBetweenTwoStrings S = new ShiftDistanceBetweenTwoStrings();
        System.out.println(S.shiftDistance("abab","baba",
                new int[]{100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new int[]{1,100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}));
        System.out.println(S.shiftDistance("leet","code",
                new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}));
        System.out.println(S.shiftDistance("l","c",
                new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}));
        System.out.println(S.shiftDistance("c","c",
                new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}));
    }
}
