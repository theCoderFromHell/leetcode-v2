package medium;

public class KokoEatingBananas {
    public int minEatingSpeed(int[] piles, int h) {
        if(piles == null || piles.length == 0)
            return 0;
        return search(piles, piles.length, h);
    }
    private int search(int[] piles, int length, int h) {
        int end = 0;
        for (int i = 0; i < length; i++)
            end = Math.max(end, piles[i]);
        int start = 1;
        int minTime = Integer.MAX_VALUE;
        while ( start <= end) {
            int mid = start + (end-start)/2;
            if(canEatAll(piles, mid, h)) {
                minTime = Math.min(minTime, mid);
                end = mid - 1;
            } else {
                start = mid+1;
            }
        }
        return minTime;
    }

    private boolean canEatAll(int[] piles, int mid, int hours) {
        long count = 0;
        for (int pile : piles) {
            count += ((pile%mid ==0) ? (pile/mid) : (pile/mid + 1));
        }
        return (count <= hours);
    }

    public static void main(String[] args) {
        KokoEatingBananas K = new KokoEatingBananas();
        System.out.println(K.minEatingSpeed(new int[]{805306368,805306368,805306368}, 1000000000));
        System.out.println(K.minEatingSpeed(new int[]{3,6,7,11}, 8));
        System.out.println(K.minEatingSpeed(new int[]{30,11,23,4,20}, 5));
        System.out.println(K.minEatingSpeed(new int[]{30,11,23,4,20}, 6));
    }
}
