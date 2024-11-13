package medium;

public class FindTheStudentThatWillReplaceTheChalk {
    public int chalkReplacer(int[] chalk, int k) {
        int N = chalk.length;
        long total = 0;
        long[] cumulative = new long[N];
        for (int i = 0; i < N; i++) {
            total += chalk[i];
            cumulative[i] = total;
        }
        long totalChalks = k;
        totalChalks = totalChalks % total;
        return binarySearch(cumulative, 0, N-1, N, totalChalks);
    }

    private int binarySearch(long[] chalk, int start, int end, int length, long value) {
        while (start < end) {
            int mid = start + (end -  start)/2;
            if (chalk[mid] == value)
                return (mid == length-1) ? 0 : mid+1;
            if (chalk[mid] < value)
                start = mid +1;
            else if (chalk[mid] > value)
                end = mid;
        }
        return start;
    }

    public static void main(String[] args) {
        FindTheStudentThatWillReplaceTheChalk F = new FindTheStudentThatWillReplaceTheChalk();
        System.out.println(F.chalkReplacer(new int[]{5,1,5}, 22));
        System.out.println(F.chalkReplacer(new int[]{3,4,1,2}, 25));
        //System.out.println(F.chalkReplacer(new int[]{}, ));
    }
}
