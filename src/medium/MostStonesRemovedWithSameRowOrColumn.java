package medium;

import java.util.HashSet;
import java.util.Objects;

public class MostStonesRemovedWithSameRowOrColumn {
    public int removeStones(int[][] stones) {
        int N = stones.length;
        if (N == 0 || N == 1 )
            return 0;
        int count = N;
        HashSet<IntegerPair> visited = new HashSet<>();
        for (int i = 0; i < N; i++) {
            if (!visited.contains(new IntegerPair(stones[i][0], stones[i][1]))) {
                dfs(stones, N, visited, stones[i][0], stones[i][1]);
                count--;
            }
        }
        return count;
    }

    private void dfs(int[][] stones, int size, HashSet<IntegerPair> visited, int x, int y) {
        visited.add(new IntegerPair(x, y));
        for (int i = 0; i < size; i++) {
            if (!visited.contains(new IntegerPair(stones[i][0], stones[i][1])) && (stones[i][0] == x || stones[i][1] == y))
                dfs(stones, size, visited, stones[i][0], stones[i][1]);
        }
    }

    public static void main(String[] args) {

    }
}

class IntegerPair {
    Integer key;
    Integer value;

    public IntegerPair(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegerPair pair = (IntegerPair) o;

        if (!Objects.equals(key, pair.key)) return false;
        return Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}