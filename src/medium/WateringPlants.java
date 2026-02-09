package medium;

public class WateringPlants {
    public int wateringPlants(int[] plants, int capacity) {
        int size = plants.length;
        int can = capacity;
        int distance = 0;
        for (int i = 0; i < size; i++) {
            if (plants[i] > can) {
                 distance += 2 * i;
                 can = capacity;
            }
            can -= plants[i];
            distance++;
        }
        return distance;
    }

    public static void main(String[] args) {
        WateringPlants W = new WateringPlants();
        System.out.println("Test 1: " + W.wateringPlants(new int[]{2,2,3,3}, 5) + " (Expected: 14)");
        System.out.println("Test 2: " + W.wateringPlants(new int[]{1,1,1,4,2,3}, 4) + " (Expected: 30)");
        System.out.println("Test 3: " + W.wateringPlants(new int[]{7,7,7,7,7,7,7}, 8) + " (Expected: 49)");
        System.out.println("Test 4: " + W.wateringPlants(new int[]{1}, 5) + " (Expected: 1)");
        System.out.println("Test 5: " + W.wateringPlants(new int[]{1,2,3,4,5}, 15) + " (Expected: 5)");
        System.out.println("Test 6: " + W.wateringPlants(new int[]{5,5,5}, 5) + " (Expected: 9)");
        System.out.println("Test 7: " + W.wateringPlants(new int[]{3,2,4,2,1}, 6) + " (Expected: 17)");
    }
}
