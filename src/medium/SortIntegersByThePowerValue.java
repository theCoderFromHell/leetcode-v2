package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortIntegersByThePowerValue {
    public static int getKth(int lo, int hi, int k) {
        int[] dp = new int[1001];
        Arrays.fill(dp, -1);
        int x = 1;
        int p = 0;
        int count = 0;
        while (x < 1000) {
            dp[x] = count++;
            x = (int) Math.pow(2, ++p);
        }
        List<PowerValue> list = new ArrayList<>();
        for (int i = lo; i <= hi; i++) {
            if (dp[i] == -1)
                dp[i] = calculate(dp, i, 0);
            list.add(new PowerValue(i, dp[i]));
        }
        Collections.sort(list);
        return list.get(k-1).number;
    }

    private static int calculate(int[] dp, int idx, int count) {
        if (idx <= 1000 && dp[idx] != -1)
            return count + dp[idx];
        if (idx % 2 == 1)
            return calculate(dp, 3 * idx + 1, count + 1);
        else
            return calculate(dp, idx / 2, count + 1);
    }

    public static void main(String[] args) {
        System.out.println(getKth(12, 15, 2));
        System.out.println(getKth(7, 11, 4));
    }
}
class PowerValue implements Comparable<PowerValue>{
    int number;
    int steps;

    public PowerValue(int number, int steps) {
        this.number = number;
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "PowerValue{" +
                "number=" + number +
                ", steps=" + steps +
                '}';
    }

    @Override
    public int compareTo(PowerValue o) {
        return (this.steps - o.steps);
    }
}