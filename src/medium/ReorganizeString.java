package medium;

import java.util.PriorityQueue;

public class ReorganizeString {
    public String reorganizeStringV2(String s) {
        int N = s.length();
        int[] frequency = new int[26];
        for (int i = 0; i < N; i++)
            frequency[s.charAt(i) - 'a']++;
        PriorityQueue<int[]> pq = new PriorityQueue<>(((o1, o2) -> Integer.compare(o2[1], o1[1])));
        for (int i = 0; i < 26; i++) {
            if (frequency[i] > 0)
                pq.add(new int[]{i, frequency[i]});
        }
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int[] first = pq.poll();
            int firstCharacter = first[0];
            int firstFrequency = first[1];
            if (sb.isEmpty() || (firstCharacter + 'a') != sb.charAt(sb.length()-1)) {
                sb.append((char)(firstCharacter + 'a'));
                firstFrequency--;
                if (firstFrequency > 0)
                    pq.add(new int[]{firstCharacter, firstFrequency});
            } else {
                if (pq.isEmpty())
                    return "";
                int[] second = pq.poll();
                int secondCharacter = second[0];
                int secondFrequency = second[1];
                sb.append((char)(secondCharacter + 'a'));
                secondFrequency--;
                if (secondFrequency > 0)
                    pq.add(new int[]{secondCharacter, secondFrequency});
                pq.add(new int[]{firstCharacter, firstFrequency});
            }
        }
        return sb.toString();
    }

    public String reorganizeString(String s) {
        int N = s.length();
        int[] frequency = new int[26];
        for (int i = 0; i < N; i++)
            frequency[s.charAt(i) - 'a']++;
        int maxCharacter = -1;
        int maxFrequency = 0;
        for (int i = 0; i < 26; i++) {
            if (frequency[i] > maxFrequency) {
                maxFrequency = frequency[i];
                maxCharacter = i;
            }
        }
        if (maxFrequency > (N + 1) / 2)
            return "";
        char[] text = new char[N];
        int index = 0;
        while (frequency[maxCharacter] > 0 && index < N) {
            text[index] = (char) (maxCharacter + 'a');
            frequency[maxCharacter]--;
            index += 2;
        }

        for (int i = 0; i < 26; i++) {
            int freq = frequency[i];
            while (freq-- > 0) {
                if (index >= N)
                    index = 1;
                text[index] = (char) (i + 'a');
                index += 2;
            }
        }
        return new String(text);
    }

    public static void main(String[] args) {
        ReorganizeString R = new ReorganizeString();
//        System.out.println(R.reorganizeString("aab"));
//        System.out.println(R.reorganizeString("aaab"));
        System.out.println(R.reorganizeString("baaba"));

    }
}
