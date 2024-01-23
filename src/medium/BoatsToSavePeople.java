package medium;

import java.util.Arrays;

public class BoatsToSavePeople {
    public static int numRescueBoats(int[] people, int limit) {
        int N = people.length;
        Arrays.sort(people);
        int left = 0;
        int right = N-1;
        int result = 0;
        while (left <= right) {
            result++;
            if (left < right && people[left] <= limit - people[right])
                left++;
            right--;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(numRescueBoats(new int[]{1,2}, 3));
        System.out.println(numRescueBoats(new int[]{3,2,2,1}, 3));
        System.out.println(numRescueBoats(new int[]{3,5,3,4}, 5));

    }
}
