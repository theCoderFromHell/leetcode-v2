package medium;

import java.util.PriorityQueue;

public class LongestHappyString {
    public String longestDiverseString(int a, int b, int c) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1,o2) -> o2[1] - o1[1]);
        if (a > 0)
            pq.add(new int[]{0, a});
        if (b > 0)
            pq.add(new int[]{1, b});
        if (c > 0)
            pq.add(new int[]{2, c});
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (count < a+b+c) {
            int[] top = pq.poll();
            char character = (char) (top[0] + 'a');
            int frequency = top[1];
            if (sb.length() >= 2 && sb.charAt(sb.length()-1) == character && sb.charAt(sb.length()-2) == character) {
                if (pq.isEmpty())
                    return sb.toString();
                int[] nextTop = pq.poll();
                char nextCharacter = (char) (nextTop[0] + 'a');
                int nextFrequency = nextTop[1];
                sb.append(nextCharacter);
                nextFrequency--;
                if (nextFrequency > 0)
                    pq.add(new int[]{nextTop[0], nextFrequency});
            } else {
                sb.append(character);
                frequency--;
            }
            if (frequency > 0)
                pq.add(new int[]{top[0], frequency});
            count++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LongestHappyString L = new LongestHappyString();
        System.out.println(L.longestDiverseString(1,1,7));
        System.out.println(L.longestDiverseString(7,1,0));
    }

}
