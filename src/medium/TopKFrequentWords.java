package medium;

import java.util.*;

public class TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        int size = words.length;
        HashMap<String, Integer> frequency = new HashMap<>();
        for (int i = 0; i < size; i++)
            frequency.put(words[i], frequency.getOrDefault(words[i], 0) + 1);
        PriorityQueue<WordCount> wordsByFrequency = new PriorityQueue<>((o1, o2) -> {
            if (o1.count == o2.count)
                return o1.word.compareTo(o2.word);
            return o2.count - o1.count;
        });
        for (String word : frequency.keySet())
            wordsByFrequency.add(new WordCount(word, frequency.get(word)));
        List<String> result = new ArrayList<>();
        while (!wordsByFrequency.isEmpty() && k-- > 0)
            result.add(wordsByFrequency.poll().word);
        return result;
    }
    class WordCount {
        String word;
        int count;

        public WordCount(String word, int count) {
            this.count = count;
            this.word = word;
        }

        @Override
        public String toString() {
            return "WordCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

    public static void main(String[] args) {
        TopKFrequentWords T = new TopKFrequentWords();
        System.out.println(T.topKFrequent(new String[]{"i","love","leetcode","i","love","coding"}, 2));
        System.out.println(T.topKFrequent(new String[]{"the","day","is","sunny","the","the","the","sunny","is","is"}, 4));
    }
}
