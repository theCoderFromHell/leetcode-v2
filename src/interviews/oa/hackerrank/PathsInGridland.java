package interviews.oa.hackerrank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PathsInGridland {
    private BigInteger[][] combinations;

    public List<String> getSafePaths(List<String> journeys) {
        precompute(20);
        List<String> result = new ArrayList<>();
        for (String journey : journeys) {
            String[] parts = journey.split(" ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int k = Integer.parseInt(parts[2]);
            result.add(findPath(x, y, k));
        }
        return result;
    }

    private void precompute(int maxN) {
        combinations = new BigInteger[maxN + 1][maxN + 1];
        for (int n = 1; n <= maxN; n++) {
            combinations[n][0] = BigInteger.ONE;
            combinations[n][n] = BigInteger.ONE;
            for (int k = 1; k < n; k++)
                combinations[n][k] = combinations[n - 1][k - 1].add(combinations[n - 1][k]);
        }
    }

    private String findPath(int x, int y, int k) {
        StringBuilder path = new StringBuilder();
        BigInteger remaining = BigInteger.valueOf(k);
        while (x > 0 && y > 0) {
            // Compute C(x+y-1, x-1) which is the number of paths starting with H
            BigInteger count = combinations[x + y - 1][x - 1];
            if (remaining.compareTo(count) < 0) {
                path.append('H');
                x--;
            } else {
                path.append('V');
                remaining = remaining.subtract(count);
                y--;
            }
        }
        // Append remaining H's or V's
        while (x-- > 0)
            path.append('H');
        while (y-- > 0)
            path.append('V');
        return path.toString();
    }

    public static void main(String[] args) {
        PathsInGridland P = new PathsInGridland();
        System.out.println(P.getSafePaths(List.of("1 1 0", "1 1 1")));
        System.out.println(P.getSafePaths(List.of("2 2 2", "2 2 3")));
    }
}

/*
Gridland is a 2D world divided into cells, with each cell represented by coordinates where (0,0) is the top left corner.
From any cell a resident can move either horizontally or vertically to an adjacent cell.
Each possible minimum path is a sequence of horizontal and vertical moves denoted by the characters H and V.
For instance, the path "HVHV" describes the sequence of moves from (0, 0) to (2, 2) as horizontal → vertical → horizontal → vertical.

A specific path through Gridland must be identified.
Start by generating all possible paths from the origin to the target location, represented as strings of H and V.
Order these strings in alphabetically ascending order.
Return the path at the zero-based index position of a given key value, the safe path.

Example:
input : [ '1 1 0', '1 1 1']

Output: There is a 2x2 grid and the move is from  position (0,0) to (1,1). All possible paths are
(0,0) → (0,1) → (1,1) = 'HV' and
(0,0) → (1,0) → (1,1) = 'VH'
Sorting those paths yields ['HV', 'VH']. if the key value is 0 the answer is 'HV' If it is 1, the answer is 'VH'

Function Description

Complete the function getSafePaths(List<String> journeys) in the editor with the following parameter

Input: string journeys[n] : an array of strings of three space separated integers, x, y and& k

Write an efficient method in Java

(x,y) is the destination cell from (0.0)

k :  key index value

Returns:

string[n] an array of paths where each element i contains the path string for journeys[i]

journeys array length can be upto 100000
x, y can be from 1 to 10

 */
