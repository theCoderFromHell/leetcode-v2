package medium;

public class SentenceSimilarityIII {
    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        String[] words1 = sentence1.split(" ");
        String[] words2 = sentence2.split(" ");
        if (words1.length > words2.length)
            return areSentencesSimilar(sentence2, sentence1);
        int start = 0;
        int end1 = words1.length-1, end2 = words2.length-1;
        while (start <= end1 && words1[start].equals(words2[start]))
            start++;
        while (end1 >= 0 && words1[end1].equals(words2[end2])) {
            end1--;
            end2--;
        }
        return  (end1 - start < 0);
    }

    public static void main(String[] args) {
        SentenceSimilarityIII S = new SentenceSimilarityIII();
        System.out.println(S.areSentencesSimilar("My name is Haley", "My Haley"));
        System.out.println(S.areSentencesSimilar("of", "A lot of words"));
        System.out.println(S.areSentencesSimilar("Eating right now", "Eating"));
    }
}
