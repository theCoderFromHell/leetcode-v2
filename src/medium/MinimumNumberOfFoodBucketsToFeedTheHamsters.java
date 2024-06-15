package medium;

public class MinimumNumberOfFoodBucketsToFeedTheHamsters {
    public int minimumBuckets(String hamsters) {
        int N = hamsters.length();
        StringBuilder sb = new StringBuilder(hamsters);
        int count = 0;
        for (int i = 1; i < N-1; i++) {
            if (sb.charAt(i) == '.' && sb.charAt(i-1) == 'H' && sb.charAt(i+1) == 'H') {
                sb.setCharAt(i, 'F');
                sb.setCharAt(i-1, 'D');
                sb.setCharAt(i+1, 'D');
                count++;
            }
        }
        for (int i = 0; i < N; i++) {
            if(sb.charAt(i) == 'H') {
                if (i > 0 && sb.charAt(i - 1) == '.') {
                    count++;
                    sb.setCharAt(i - 1, 'F');
                    sb.setCharAt(i, 'D');
                } else if (i < N - 1 && sb.charAt(i + 1) == '.') {
                    count++;
                    sb.setCharAt(i + 1, 'F');
                    sb.setCharAt(i, 'D');
                }else
                    return -1;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        MinimumNumberOfFoodBucketsToFeedTheHamsters m = new MinimumNumberOfFoodBucketsToFeedTheHamsters();
        System.out.println(m.minimumBuckets("H..H"));
        System.out.println(m.minimumBuckets(".H.H."));
        System.out.println(m.minimumBuckets(".HHH."));
    }
}
