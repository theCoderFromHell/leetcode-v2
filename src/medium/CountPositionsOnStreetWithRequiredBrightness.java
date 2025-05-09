package medium;

public class CountPositionsOnStreetWithRequiredBrightness {
    public int meetRequirement(int n, int[][] lights, int[] requirement) {
        int size = lights.length;
        int[] line = new int[100002];
        int position, range, minRange, maxRange;
        for (int i = 0; i < size; i++) {
            position = lights[i][0];
            range = lights[i][1];
            minRange = Math.max(0, position - range);
            maxRange = Math.min(n-1, position + range);
            line[minRange]++;
            line[maxRange+1]--;
        }
        int length = requirement.length;
        int value = 0;
        int count = 0;
        for (int i = 0; i < length; i++) {
            value += line[i];
            if (value >= requirement[i])
                count++;
        }
        return count;
    }
}
