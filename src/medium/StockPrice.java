package medium;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

public class StockPrice {
    PriorityQueue<Stock> min;
    PriorityQueue<Stock> max;
    HashMap<Integer,Integer> map;
    int latestTime;

    public StockPrice() {
        this.min = new PriorityQueue<>(Comparator.comparing(Stock::getPrice));
        this.max = new PriorityQueue<>(Comparator.comparing(Stock::getPrice, Comparator.reverseOrder()));
        this.map = new LinkedHashMap<>();
        this.latestTime = 0;
    }

    public void update(int timestamp, int price) {
        if (timestamp > latestTime)
            latestTime = timestamp;
        map.put(timestamp, price);
        min.add(new Stock(timestamp, price));
        max.add(new Stock(timestamp, price));
    }

    public int current() {
        return map.get(latestTime);
    }

    public int maximum() {
        while (!max.isEmpty()) {
            Stock stock = max.peek();
            if (stock.price == map.get(stock.time))
                return stock.price;
            else
                max.remove();
        }
        return -1;
    }

    public int minimum() {
        while (!min.isEmpty()) {
            Stock stock = min.peek();
            if (stock.price == map.get(stock.time))
                return stock.price;
            else
                min.remove();
        }
        return -1;
    }

    class Stock {
        int time;
        int price;
        public Stock(int time, int price) {
            this.time = time;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Stock{" +
                    "time=" + time +
                    ", price=" + price +
                    '}';
        }

        public int getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        StockPrice stockPrice = new StockPrice();
        stockPrice.update(1, 10);
        stockPrice.update(2, 5);
        System.out.println(stockPrice.current());
        System.out.println(stockPrice.maximum());
        stockPrice.update(1, 3);
        System.out.println(stockPrice.maximum());
        stockPrice.update(4, 2);
        System.out.println(stockPrice.minimum());

    }
}

/**
 * Your StockPrice object will be instantiated and called as such:
 * StockPrice obj = new StockPrice();
 * obj.update(timestamp,price);
 * int param_2 = obj.current();
 * int param_3 = obj.maximum();
 * int param_4 = obj.minimum();
 */