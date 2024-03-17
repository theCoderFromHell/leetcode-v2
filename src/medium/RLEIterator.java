package medium;

public class RLEIterator {
    int[] encoding;
    int idx;
    int size;

    public RLEIterator(int[] encoding) {
        this.idx = 0;
        this.encoding = encoding;
        this.size = encoding.length;
    }

    public int next(int n) {
        while (n > 0) {
            if (idx >= size)
                return -1;
            if (encoding[idx] == 0) {
                idx += 2;
                continue;
            }
            int value = encoding[idx+1];
            int available = Math.min(n, encoding[idx]);
            n -= available;
            encoding[idx] -= available;
            if (encoding[idx] == 0)
                idx += 2;
            if (n == 0)
                return value;
        }
        return -1;
    }
}

/**
 * Your RLEIterator object will be instantiated and called as such:
 * RLEIterator obj = new RLEIterator(encoding);
 * int param_1 = obj.next(n);
 */