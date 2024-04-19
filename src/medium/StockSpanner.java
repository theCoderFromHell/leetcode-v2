package medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StockSpanner {
    Stack<Integer> stack;
    List<Integer> stockPrice;

    public StockSpanner() {
        this.stack = new Stack<>();
        this.stockPrice = new ArrayList<>();
    }

    public int next(int price) {
        while (!stack.empty() && stockPrice.get(stack.peek()) < price) {
            stack.pop();
        }
        int result = (stack.empty()) ? stockPrice.size() + 1 : stockPrice.size() - stack.peek();
        stack.add(stockPrice.size());
        stockPrice.add(price);
        return result;
    }

    public static void main(String[] args) {
        StockSpanner s = new StockSpanner();
        System.out.println(s.next(100));
        System.out.println(s.next(80));
        System.out.println(s.next(60));
        System.out.println(s.next(70));
        System.out.println(s.next(60));
        System.out.println(s.next(75));
        System.out.println(s.next(85));
    }
}
