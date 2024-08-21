package medium;

public class FindTheCityWithTheSmallestNumberOfNeighborsAtAThresholdDistance {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] path = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                path[i][j] = Integer.MAX_VALUE;
                path[i][i] = 0;
            }
        }

        for (int[] edge : edges) {
            int source = edge[0];
            int destination = edge[1];
            int weight = edge[2];
            path[source][destination] = weight;
            path[destination][source] = weight;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (path[i][k] != Integer.MAX_VALUE && path[k][j] != Integer.MAX_VALUE) {
                        if (path[i][k] + path[k][j] < path[i][j])
                            path[i][j] = path[i][k] + path[k][j];
                    }
                }
            }
        }
        int nodeWithMinNeighbors = -1;
        int minNeighbours = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && path[i][j] <= distanceThreshold)
                    count++;
            }
            if (count <= minNeighbours) {
                nodeWithMinNeighbors = i;
                minNeighbours = count;
            }
        }
        return nodeWithMinNeighbors;
    }

    public static void main(String[] args) {
        FindTheCityWithTheSmallestNumberOfNeighborsAtAThresholdDistance F = new FindTheCityWithTheSmallestNumberOfNeighborsAtAThresholdDistance();
        System.out.println(F.findTheCity(4, new int[][]{{0,1,3},{1,2,1},{1,3,4},{2,3,1}}, 4));
        System.out.println(F.findTheCity(5, new int[][]{{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}}, 2));
    }
}
