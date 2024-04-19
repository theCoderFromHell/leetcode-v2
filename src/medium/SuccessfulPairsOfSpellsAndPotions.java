package medium;

import java.util.Arrays;

public class SuccessfulPairsOfSpellsAndPotions {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int N = spells.length;
        Arrays.sort(potions);
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            int start = 0, end = potions.length - 1;
            int mid;
            if ((long) potions[start] * spells[i] >= success) {
                result[i] = potions.length;
                continue;
            }
            if ((long) potions[end] * spells[i]  < success) {
                result[i] = 0;
                continue;
            }
            while (start < end) {
                mid = start + (end - start)/2;
                if ((long) potions[mid] * spells[i]  >= success)
                    end = mid;
                else
                    start = mid + 1;
            }
            result[i] = potions.length - end;
        }
        for (int i = 0; i < N; i++) {
            System.out.print(result[i] + " ");
        }
        return result;
    }

    public static void main(String[] args) {
        SuccessfulPairsOfSpellsAndPotions s = new SuccessfulPairsOfSpellsAndPotions();
        System.out.println(s.successfulPairs(new int[]{5,1,3}, new int[]{1,2,3,4,5}, 7));
        System.out.println(s.successfulPairs(new int[]{3,1,2}, new int[]{8,5,8}, 16));
    }


}
