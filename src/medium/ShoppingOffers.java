package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShoppingOffers {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        HashMap<List<Integer>, Integer> dp = new HashMap<>();
        int size = price.size();
        return solve(price, size, special, needs, dp);
    }

    private int solve(List<Integer> price, int size, List<List<Integer>> special, List<Integer> needs, HashMap<List<Integer>, Integer> dp) {
        if (needs.isEmpty())
            return 0;
        if (dp.containsKey(needs))
            return dp.get(needs);
        int minPrice = directBuy(price, size, needs);
        for (List<Integer> offer : special) {
            List<Integer> clone = new ArrayList<>(needs);
            int i;
            int count = 0;
            for (i = 0; i < size; i++) {
                int diff = clone.get(i) - offer.get(i);
                if (diff < 0)
                    break;
                if (diff == 0)
                    count++;
                clone.set(i, diff);
            }
            if (i == size)
                minPrice = Math.min(minPrice, offer.get(size) + ((count == size) ? 0 : solve(price, size, special, clone, dp)));
        }
        dp.put(needs, minPrice);
        return minPrice;
    }

    private int directBuy(List<Integer> price, int size, List<Integer> needs) {
        int result = 0;
        for (int i = 0; i < size; i++)
            result += (price.get(i) * needs.get(i));
        return result;
    }

    public static void main(String[] args) {
        ShoppingOffers S = new ShoppingOffers();
        System.out.println(S.shoppingOffers(Arrays.asList(2,5), Arrays.asList(Arrays.asList(3,0,5), Arrays.asList(1,2,10)), Arrays.asList(3,2)));
        System.out.println(S.shoppingOffers(Arrays.asList(2,3,4), Arrays.asList(Arrays.asList(1,1,0,4), Arrays.asList(2,2,1,9)), Arrays.asList(1,2,1)));
    }
}
