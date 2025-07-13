package medium;

public class CountNumberOfTeams {
    public int numTeams(int[] rating) {
        int size = rating.length;
        int[] left = new int[size];
        int[] right = new int[size];
        int count = 0;
        int result = 0;
        for (int i = 1; i < size; i++) {
            count = 0;
            for (int j = i-1; j >= 0 ; j--) {
                if (rating[j] < rating[i]) {
                    count++;
                    if (left[j] > 0)
                        result += left[j];
                }
            }
            left[i] = count;
        }
        for (int i = size-2; i >= 0; i--) {
            count = 0;
            for (int j = i+1; j < size; j++) {
                if (rating[j] < rating[i]) {
                    count++;
                    if (right[j] > 0)
                        result += right[j];
                }
            }
            right[i] = count;
        }
        return result;
    }

    public static void main(String[] args) {
        CountNumberOfTeams C = new CountNumberOfTeams();
        System.out.println(C.numTeams(new int[]{2,5,3,4,1}));
        System.out.println(C.numTeams(new int[]{2,1,3}));
        System.out.println(C.numTeams(new int[]{1,2,3,4}));
    }
}
