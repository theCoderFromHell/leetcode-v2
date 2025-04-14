package medium;

import java.util.HashMap;
import java.util.PriorityQueue;

public class SenderWithLargestWordCount {
    public String largestWordCount(String[] messages, String[] senders) {
        int size = messages.length;
        HashMap<String, Integer> wordCountBySender = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String[] words = messages[i].split(" ");
            wordCountBySender.put(senders[i], wordCountBySender.getOrDefault(senders[i], 0) + words.length);
        }
        PriorityQueue<Sender> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1.wordCount == o2.wordCount)
                return o2.sender.compareTo(o1.sender);
            return o2.wordCount - o1.wordCount;
        });
        for (String sender : wordCountBySender.keySet())
            pq.add(new Sender(sender, wordCountBySender.get(sender)));
        return pq.poll().sender;
    }

    class Sender {
        String sender;
        int wordCount;

        public Sender(String sender, int wordCount) {
            this.sender = sender;
            this.wordCount = wordCount;
        }

        @Override
        public String toString() {
            return "Sender{" +
                    "sender='" + sender + '\'' +
                    ", wordCount=" + wordCount +
                    '}';
        }
    }

    public static void main(String[] args) {
        SenderWithLargestWordCount S = new SenderWithLargestWordCount();
        System.out.println(S.largestWordCount(new String[]{"Hello userTwooo","Hi userThree","Wonderful day Alice","Nice day userThree"}, new String[]{"Alice","userTwo","userThree","Alice"}));
        System.out.println(S.largestWordCount(new String[]{"How is leetcode for everyone","Leetcode is useful for practice"}, new String[]{"Bob","Charlie"}));
    }
}
