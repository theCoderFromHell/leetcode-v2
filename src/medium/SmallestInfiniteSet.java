package medium;

import java.util.TreeSet;

public class SmallestInfiniteSet {
    boolean[] set;
    public SmallestInfiniteSet() {
        this.set = new boolean[1001];
        for (int i = 1; i <= 1000; i++)
            set[i] = true;
    }

    public int popSmallest() {
        for (int i = 1; i <=1000; i++) {
            if (set[i]) {
                set[i] = false;
                return i;
            }
        }
        return -1;
    }

    public void addBack(int num) {
        set[num] = true;
    }
}
