package interviews.online.assessment.hackerrank;

import java.util.*;

public class FinalPriceOfItems {
    public void finalPrice(List<Integer> prices) {
        int size = prices.size();
        int[] finalCost = new int[size];
        List<Integer> fullPriceItems = new ArrayList<>();
        Stack<Integer> stack = new Stack<>(); // stores indices of candidates for discount

        // Traverse from right to left
        for (int i = size - 1; i >= 0; i--) {
            int price = prices.get(i);

            // Pop elements greater than current price (not eligible for discount)
            while (!stack.isEmpty() && prices.get(stack.peek()) > price) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                // Apply discount: next smaller or equal price found
                finalCost[i] = price - prices.get(stack.peek());
            } else {
                // No discount
                finalCost[i] = price;
                fullPriceItems.add(i);
            }

            // Push current index to stack
            stack.push(i);
        }

        // Calculate total cost
        int total = 0;
        for (int cost : finalCost) {
            total += cost;
        }

        // Output
        System.out.println(total);
        Collections.sort(fullPriceItems);
        for (int i = 0; i < fullPriceItems.size(); i++) {
            System.out.print(fullPriceItems.get(i));
            if (i < fullPriceItems.size() - 1)
                System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        FinalPriceOfItems F = new FinalPriceOfItems();
        F.finalPrice(Arrays.asList(2, 3, 1, 2, 4, 2));
    }
}
/*
A shopkeeper arranges items in a list for a sale. Starting from the left,
each item is sold at its full price minus the price of the first item to its right that is of equal or lower price.
If no such item exists, the current item is sold at its full price. Print the sum of the final cost for all items,
then on the next line, print space-separated, 0-based indices of items that are sold at full price, in ascending order.

For example, consider the item prices [2, 3, 1, 2, 4, 2].

Items at indices 0 and 1 are each discounted by 1 unit, the first equal or lower price to their right.
Item 2, priced at 1 unit, sells at full price as there are no equal or lower-priced items to its right.
Item 3, priced at 2 units, is discounted by 2 units to 0.
Item 4, priced at 4 units, is discounted by 2 units to 2.

The final item, priced at 2 units, sold at full price as there are no items to its right.

The total cost amounts to 1+2+1+0+2+2=8 units, with full price items at indices [2, 5] using 0-based indexing.
The output is:
8
2 5

 */
