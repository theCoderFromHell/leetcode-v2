package interviews.microsoft;

import java.io.*;
import java.util.*;

public class FindAnagramsInDictionary {
    HashSet<String> dictionary;

    public FindAnagramsInDictionary (){}

    public FindAnagramsInDictionary (File file) throws IOException {
        this.dictionary = new HashSet<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String word;
        while (br.readLine() != null) {
            word = br.readLine();
            dictionary.add(word);
        }
    }

    public List<String> findValidAnagrams(String word) {
        int length = word.length();
        List<String> anagrams = findAnagrams(word, length);
        List<String> result = new ArrayList<>();
        for (String anagram : anagrams) {
            if (dictionary.contains(anagram)) {
                result.add(anagram);
            }
        }
        return result;
    }

    public List<String> findAnagrams(String word, int length) {
        List<String> anagrams = new ArrayList<>();
        char[] array = word.toCharArray();
        Arrays.sort(array);
        word = new String(array);
        StringBuffer sb = new StringBuffer(word);
        generate(sb, length, 0, anagrams);
        return anagrams;
    }

    private void generate(StringBuffer word, int length, int index, List<String> anagrams) {
        if (index == length) {
            anagrams.add(new String(word));
            return;
        }
        for (int i = index; i < length; i++) {
            if (i > index && word.charAt(i-1) == word.charAt(i))
                continue;
            swap(word, index, i);
            generate(word, length, index+1, anagrams);
            swap(word, index, i);
        }
    }

    private void swap(StringBuffer word, int i, int j) {
        char temp = word.charAt(i);
        word.setCharAt(i, word.charAt(j));
        word.setCharAt(j, temp);
    }


    public static void main(String[] args) {
        FindAnagramsInDictionary F = new FindAnagramsInDictionary();
        System.out.println(F.findAnagrams("ABAB", 4));
    }
}
