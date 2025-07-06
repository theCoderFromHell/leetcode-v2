package medium;

import java.util.HashMap;

public class ApplyDiscountEveryNOrders {
    public static void main(String[] args) {
        Cashier cashier = new Cashier(3, 50,
                new int[]{1,2,3,4,5,6,7},
                new int[]{100,200,300,400,300,200,100});
        System.out.println(cashier.getBill(new int[]{1,2}, new int[]{1,2}));
        System.out.println(cashier.getBill(new int[]{3,7}, new int[]{10,10}));
        System.out.println(cashier.getBill(new int[]{1,2,3,4,5,6,7}, new int[]{1,1,1,1,1,1,1}));
        System.out.println(cashier.getBill(new int[]{4}, new int[]{10}));
        System.out.println(cashier.getBill(new int[]{7,3}, new int[]{10,10}));
        System.out.println(cashier.getBill(new int[]{7,5,3,1,6,4,2}, new int[]{10,10,10,9,9,9,7}));
        System.out.println(cashier.getBill(new int[]{2,3,5}, new int[]{5,3,2}));
    }
}

class Cashier {
    int n;
    int count;
    int discount;
    HashMap<Integer, Integer> priceMap;

    public Cashier(int n, int discount, int[] products, int[] prices) {
        this.n = n;
        this.discount = discount;
        this.count = 0;
        this.priceMap = new HashMap<>();
        int size = products.length;
        for (int i = 0; i < size; i++)
            priceMap.put(products[i], prices[i]);
    }

    public double getBill(int[] product, int[] amount) {
        count++;
        int size = product.length;
        double total = 0;
        for (int i = 0; i < size; i++)
            total += ((double) priceMap.get(product[i]) * amount[i]);
        if (count % n == 0)
            total *= ((double) (100 - discount) / 100);
        return total;
    }
}