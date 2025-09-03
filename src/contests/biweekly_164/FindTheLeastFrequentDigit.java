package contests.biweekly_164;

import java.util.HashMap;

public class FindTheLeastFrequentDigit {
    public int getLeastFrequentDigit(int n) {
        HashMap<Integer,Integer> frequency = new HashMap<>();
        while (n > 0) {
            int value = n % 10;
            frequency.put(value, frequency.getOrDefault(value, 0) + 1);
            n /= 10;
        }
        int minFrequency = Integer.MAX_VALUE;
        int digit = -1;
        for (int key : frequency.keySet()) {
            if (frequency.get(key) < minFrequency || (frequency.get(key) == minFrequency && key < digit)) {
                digit = key;
                minFrequency = frequency.get(key);
            }
        }
        return digit;
    }

    public static void main(String[] args) {
        FindTheLeastFrequentDigit S = new FindTheLeastFrequentDigit();
        System.out.println(S.getLeastFrequentDigit(1553322));
        System.out.println(S.getLeastFrequentDigit(723344511));
        System.out.println(S.getLeastFrequentDigit(244431112));
    }
}
