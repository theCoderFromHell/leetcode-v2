package medium;

public class PaintFence {
    public int numWays(int n, int k) {
        if (n == 1)
            return k;
        if (k == 1)
            return (n < 3) ? 1 : 0;
        int one = k;
        int two = 0;
        for (int i = 2; i <= n; i++) {
            int temp = two;
            two = one;
            one = (temp + one) * (k-1);
        }
        return (one + two);
    }
}
