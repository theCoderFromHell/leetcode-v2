package medium;

import java.util.PriorityQueue;

public class FindScoreOfAnArrayAfterMarkingAllElements {
    public long findScore(int[] nums) {
        int N = nums.length;
        PriorityQueue<NumberIndex> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++)
            pq.add(new NumberIndex(nums[i], i));
        long result = 0;
        boolean[] hash = new boolean[N];
        while (!pq.isEmpty()) {
            NumberIndex n = pq.poll();
            if (!hash[n.index]) {
                result += n.value;
                hash[n.index] = true;
                if (n.index > 0 && !hash[n.index-1])
                    hash[n.index-1] = true;
                if (n.index < N-1 && !hash[n.index+1])
                    hash[n.index+1] = true;
            }
        }
        return result;
    }
}
class NumberIndex implements Comparable<NumberIndex>{
    int value;
    int index;

    public NumberIndex(int value, int index) {
        this.value = value;
        this.index = index;
    }

    @Override
    public int compareTo(NumberIndex o) {
        if (this.value == o.value)
            return (this.index - o.index);
        return (this.value - o.value);
    }

    @Override
    public String toString() {
        return "NumberIndex{" +
                "value=" + value +
                ", index=" + index +
                '}';
    }
}