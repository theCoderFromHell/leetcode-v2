package medium;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CarPooling {
    public boolean carPooling(int[][] trips, int capacity) {
        int size = trips.length;
        List<int[]> inOut = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            inOut.add(new int[]{trips[i][1], trips[i][0]});
            inOut.add(new int[]{trips[i][2], -1*trips[i][0]});
        }
        inOut.sort(((o1, o2) -> {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            return o1[0] - o2[0];
        }));
        int maxPassengers = 0;
        for (int i = 0; i < 2*size; i++) {
            maxPassengers += inOut.get(i)[1];
            if (maxPassengers > capacity)
                return false;
        }
        return true;
    }
    public boolean carPoolingV2(int[][] trips, int capacity) {
        TreeMap<Integer, Integer> people = new TreeMap<>();
        for (int[] trip : trips) {
            int start = people.getOrDefault(trip[1], 0);
            int end = people.getOrDefault(trip[2], 0);
            people.put(trip[1], start + trip[0]);
            people.put(trip[2], end - trip[0]);
        }
        int current = 0;
        for (int times : people.keySet()) {
            current += people.get(times);
            if (current > capacity)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        CarPooling C = new CarPooling();
        System.out.println(C.carPooling(new int[][]{{2,1,5},{3,3,7}}, 4));
        System.out.println(C.carPooling(new int[][]{{9,3,6},{8,1,7},{6,6,8},{8,4,9},{4,2,9}}, 28));
        System.out.println(C.carPooling(new int[][]{{8, 16, 20}, {9, 18, 19}, {7, 13, 16}, {7, 13, 16}, {8, 15, 18}, {3, 17, 18}, {6, 6, 18}, {1, 2, 6}, {5, 3, 7}, {1, 16, 20}, {3, 12, 20}, {8, 18, 20}, {5, 7, 17}, {9, 14, 15}, {7, 9, 17}, {7, 12, 18}, {6, 15, 19}, {6, 18, 19}, {8, 18, 20}, {2, 12, 16}}, 57));
        System.out.println(C.carPooling(new int[][]{{8, 16, 20}, {9, 18, 19}, {7, 13, 16}, {7, 13, 16}, {8, 15, 18}, {3, 17, 18}, {6, 6, 18}, {1, 2, 6}, {5, 3, 7}, {1, 16, 20}, {3, 12, 20}, {8, 18, 20}, {5, 7, 17}, {9, 14, 15}, {7, 9, 17}, {7, 12, 18}, {6, 15, 19}, {6, 18, 19}, {8, 18, 20}, {2, 12, 16}}, 58));
        System.out.println(C.carPooling(new int[][]{{8, 16, 20}, {9, 18, 19}, {7, 13, 16}, {7, 13, 16}, {8, 15, 18}, {3, 17, 18}, {6, 6, 18}, {1, 2, 6}, {5, 3, 7}, {1, 16, 20}, {3, 12, 20}, {8, 18, 20}, {5, 7, 17}, {9, 14, 15}, {7, 9, 17}, {7, 12, 18}, {6, 15, 19}, {6, 18, 19}, {8, 18, 20}, {2, 12, 16}}, 59));
    }
}
