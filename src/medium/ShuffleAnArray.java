package medium;

import common.Util;
import hard.NQueens;

import java.util.Arrays;
import java.util.Random;

public class ShuffleAnArray {
    int[] original;
    int [] current;
    Random random;
    public ShuffleAnArray(int[] nums) {
        this.original = nums;
        this.current = original.clone();
        this.random = new Random();
    }

    public int[] reset() {
        current = original.clone();
        return current;
    }

    public int[] shuffle() {
        int N = current.length;
        for (int i = 0; i < N; i++) {
            int index = getRandomIndexBetween(i, N);
            int temp = current[i];
            current[i] = current[index];
            current[index] = temp;
        }
        return current;
    }

    int getRandomIndexBetween(int start, int end) {
        return random.nextInt(end - start) + start;
    }

    public static void main(String[] args) {
        ShuffleAnArray S = new ShuffleAnArray(new int[]{1,2,3});
        Util.printArray(S.shuffle());
        Util.printArray(S.reset());
        Util.printArray(S.shuffle());
    }
}
