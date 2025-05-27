package medium;

public class MinimumSpeedToArriveOnTime {
    public int minSpeedOnTime(int[] dist, double hour) {
        int size = dist.length;
        int start = 1, end = 10000000;
        int mid;
        int result = Integer.MAX_VALUE;
        while (start <= end) {
            mid = start + (end - start)/2;
            if (works(dist, size, hour, mid)) {
                result = Math.min(result, mid);
                end = mid - 1;
            } else
                start = mid + 1;
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private boolean works(int[] dist, int size, double hour, int speed) {
        double time = 0;
        double duration;
        for (int i = 0; i < size; i++) {
            if (dist[i] % speed == 0)
                time += (double) dist[i] / speed;
            else {
                duration = (double) dist[i] / speed;
                time += i != size - 1 ? (int) (duration + 1.00) : duration;
            }
        }
        return time <= hour ;
    }

    public static void main(String[] args) {
        MinimumSpeedToArriveOnTime M = new MinimumSpeedToArriveOnTime();
        System.out.println(M.minSpeedOnTime(new int[]{1,3,2}, 6));
        System.out.println(M.minSpeedOnTime(new int[]{1,3,2}, 2.7));
        System.out.println(M.minSpeedOnTime(new int[]{1,3,2}, 1.9));
    }
}
