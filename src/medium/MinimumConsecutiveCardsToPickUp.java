package medium;

import java.util.HashMap;

public class MinimumConsecutiveCardsToPickUp {
    public int minimumCardPickup(int[] cards) {
        int size = cards.length;
        HashMap<Integer,Integer> lastIndex = new HashMap<>();
        int minCards = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (lastIndex.containsKey(cards[i]))
                minCards = Math.min(minCards, i - lastIndex.get(cards[i]) + 1);
            lastIndex.put(cards[i], i);
        }
        return minCards == Integer.MAX_VALUE ? -1 : minCards;
    }

    public static void main(String[] args) {
        MinimumConsecutiveCardsToPickUp M = new MinimumConsecutiveCardsToPickUp();
        System.out.println(M.minimumCardPickup(new int[]{3,4,2,3,4,7}));
        System.out.println(M.minimumCardPickup(new int[]{1,0,5,3}));
    }
}
