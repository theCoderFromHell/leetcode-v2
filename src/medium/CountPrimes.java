package medium;

import java.util.Collections;

public class CountPrimes {
    public int countPrimes(int n) {
        if (n == 0 || n == 1)
            return 0;
        boolean[] numbers = new boolean[n];
        numbers[0] = true;
        numbers[1] = true;
        int count = 0;
        for (int i = 2; i*i < n ; i++) {
            if (!numbers[i]) {
                for (int j = i*i; j < n; j+= i) {
                    if (!numbers[j]) {
                        numbers[j] = true;
                        count++;
                    }
                }
            }
        }
        return (n - count - 2);
    }

    public static void main(String[] args) {
        CountPrimes C = new CountPrimes();
        System.out.println(C.countPrimes(499979));
        System.out.println(C.countPrimes(100));
    }

}
