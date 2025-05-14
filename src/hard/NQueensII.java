package hard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NQueensII {
    public int totalNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        List<String> arrangement = new ArrayList<>();
        HashSet<Integer> columns = new HashSet<>();
        HashSet<Integer> positiveDiagonal = new HashSet<>();
        HashSet<Integer> negativeDiagonal = new HashSet<>();
        solve(0, arrangement, result, columns, positiveDiagonal, negativeDiagonal, n);
        return result.size();
    }

    private void solve(int row, List<String> arrangement, List<List<String>> result, HashSet<Integer> columns,
                       HashSet<Integer> positiveDiagonal, HashSet<Integer> negativeDiagonal, int n) {
        if (row == n) {
            result.add(new ArrayList<>(arrangement));
            return;
        }
        String rowArrangement = ".".repeat(n);
        for (int column = 0; column < n; column++) {
            if (columns.contains(column)
                    || positiveDiagonal.contains(row - column)
                    || negativeDiagonal.contains(row + column))
                continue;
            StringBuilder sb = new StringBuilder(rowArrangement);
            sb.setCharAt(column, 'Q');
            arrangement.addLast(sb.toString());
            columns.add(column);
            positiveDiagonal.add(row - column);
            negativeDiagonal.add(row + column);
            solve(row+1, arrangement, result, columns, positiveDiagonal, negativeDiagonal, n);
            sb.setCharAt(column, '.');
            arrangement.removeLast();
            columns.remove(column);
            positiveDiagonal.remove(row - column);
            negativeDiagonal.remove(row + column);
        }
    }

    public static void main(String[] args) {
        NQueensII N = new NQueensII();
        System.out.println(N.totalNQueens(4));
        System.out.println(N.totalNQueens(1));
        System.out.println(N.totalNQueens(2));
    }
}
