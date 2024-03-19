package medium;

public class RandomPickWithWeight {
    int[] prefixSum;
    int totalSum;
    int size;

    public RandomPickWithWeight(int[] w) {
        this.size = w.length;
        this.prefixSum = new int[size];
        int curr = 0;
        for (int i = 0; i < size; i++) {
            curr +=  w[i];
            prefixSum[i] = curr;
        }
        this.totalSum = curr;
    }

    public int pickIndex() {
        double sweetSpot = totalSum * Math.random();
        int start = 0, end = size-1;
        int mid = 0;
        while (start < end) {
            mid = start + (end - start)/2;
            if (sweetSpot < prefixSum[mid])
                end = mid;
            else
                start = mid + 1;
        }
        return mid;

    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(w);
 * int param_1 = obj.pickIndex();
 */