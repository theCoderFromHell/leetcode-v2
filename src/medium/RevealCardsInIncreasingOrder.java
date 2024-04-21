package medium;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class RevealCardsInIncreasingOrder {
    public int[] deckRevealedIncreasing(int[] deck) {
        int N = deck.length;
        if (N == 1)
            return deck;
        Arrays.sort(deck);
        if (N == 2)
            return deck;
        Deque<Integer> deque = new LinkedList<>();
        deque.add(deck[N-2]);
        deque.add(deck[N-1]);
        int index = N-3;
        while (index >= 0) {
            int back = deque.removeLast();
            deque.addFirst(back);
            deque.addFirst(deck[index]);
            index--;
        }
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            result[i] = deque.removeFirst();
        }
        return result;
    }
}
