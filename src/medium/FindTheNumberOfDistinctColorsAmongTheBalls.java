package medium;

import java.util.Arrays;
import java.util.HashMap;

public class FindTheNumberOfDistinctColorsAmongTheBalls {
    public int[] queryResults(int limit, int[][] queries) {
        int N = queries.length;
        int[] result = new int[N];
        HashMap<Integer, Integer> ballColours= new HashMap<>();
        HashMap<Integer, Integer> colourCount= new HashMap<>();
        for (int i = 0; i < N; i++) {
            int ball = queries[i][0];
            int colour = queries[i][1];
            if (ballColours.containsKey(ball)) {
                int prevColour = ballColours.remove(ball);
                colourCount.put(prevColour, colourCount.getOrDefault(prevColour, 0) - 1);
                if (colourCount.get(prevColour) <= 0)
                    colourCount.remove(prevColour);
            }
            ballColours.put(ball, colour);
            colourCount.put(colour, colourCount.getOrDefault(colour, 0) + 1);
            result[i] = colourCount.size();
        }
        return result;
    }

    public static void main(String[] args) {
        FindTheNumberOfDistinctColorsAmongTheBalls F = new FindTheNumberOfDistinctColorsAmongTheBalls();
        System.out.println(Arrays.toString(F.queryResults(4, new int[][]{{1, 4}, {2, 5}, {1, 3}, {3, 4}})));
        System.out.println(Arrays.toString(F.queryResults(4, new int[][]{{0, 1}, {1, 2}, {2, 2}, {3, 4}, {4, 5}})));
    }
}
