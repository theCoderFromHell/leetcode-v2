package medium;

public class CapacityToShipPackagesWithinDDays {
    public int shipWithinDays(int[] weights, int days) {
        int N = weights.length;
        int sum = 0;
        for (int weight : weights)
            sum += weight;
        int low = sum/days;
        int high = sum;
        int mid = -1;
        int minPossible = Integer.MAX_VALUE;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (isPossible(weights, N, mid, days)) {
                minPossible = Math.min(minPossible, mid);
                high = mid-1;
            }
            else
                low = mid+1;
        }
        return minPossible;

    }

    private boolean isPossible(int[] weights, int N, int capacity, int days) {
        int count = 0;
        int i = 0;
        int load = 0;
        while (i < N) {
            count++;
            while (i < N && load + weights[i] <= capacity)
                load += weights[i++];
            if (load == 0)
                return false;
            if (i< N && count > days)
                return false;
            load = 0;
        }
        return count <= days;
    }

    public static void main(String[] args) {
        CapacityToShipPackagesWithinDDays C = new CapacityToShipPackagesWithinDDays();
        System.out.println(C.shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5));
        System.out.println(C.shipWithinDays(new int[]{3,2,2,4,1,4}, 3));
        System.out.println(C.shipWithinDays(new int[]{1,2,3,1,1}, 4));
    }
}
