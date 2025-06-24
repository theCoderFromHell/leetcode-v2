package medium;

import java.util.Stack;

public class StockSpanner {
    Stack<int[]> stack;
    public StockSpanner() {
        this.stack = new Stack<>();
    }

    public int next(int price) {
        int currStreak = 1;
        while (!stack.empty() && stack.peek()[0] <= price) {
            int[] top = stack.pop();
            currStreak += top[1];
        }
        stack.add(new int[]{price, currStreak});
        return currStreak;
    }

    public static void main(String[] args) {
        StockSpanner S = new StockSpanner();
        System.out.println(S.next(100));
        System.out.println(S.next(80));
        System.out.println(S.next(60));
        System.out.println(S.next(70));
        System.out.println(S.next(60));
        System.out.println(S.next(75));
        System.out.println(S.next(85));
    }
}
