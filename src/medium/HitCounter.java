package medium;

import java.util.ArrayDeque;
import java.util.Deque;

public class HitCounter {
    Deque<Integer> deque;

    public HitCounter() {
        this.deque = new ArrayDeque<>();
    }

    public void hit(int timestamp) {
        deque.addFirst(timestamp);
    }

    public int getHits(int timestamp) {
        while (!deque.isEmpty() && deque.getLast() <= timestamp-300)
            deque.removeLast();
        return deque.size();
    }

    public static void main(String[] args) {
        HitCounter hitCounter = new HitCounter();
        hitCounter.hit(1);
        hitCounter.hit(2);
        hitCounter.hit(3);
        System.out.println(hitCounter.getHits(4));
        hitCounter.hit(300);
        System.out.println(hitCounter.getHits(300));
        System.out.println(hitCounter.getHits(301));




    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */