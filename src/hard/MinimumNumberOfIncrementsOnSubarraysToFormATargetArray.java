package hard;

public class MinimumNumberOfIncrementsOnSubarraysToFormATargetArray {
    public int minNumberOperations(int[] target) {
        int N = target.length;
        int prev = 0;
        int result = 0;
        for (int i = 0; i < N; i++) {
            if (target[i] > prev)
                result += (target[i] - prev);
            prev = target[i];
        }
        return result;
    }
}
