package medium;

public class NumberOfLaserBeamsInABank {
    public int numberOfBeams(String[] bank) {
        int size = bank.length;
        int previous = 0;
        int result = 0;
        for (int i = 0; i < size; i++) {
            String row = bank[i];
            int length = row.length();
            int count = 0;
            for (int j = 0; j < length; j++) {
                if (row.charAt(j) == '1')
                    count++;
            }
            result += (previous * count);
            if (count > 0)
                previous = count;
        }
        return result;
    }

    public static void main(String[] args) {
        NumberOfLaserBeamsInABank N = new NumberOfLaserBeamsInABank();
        System.out.println(N.numberOfBeams(new String[]{"011001","000000","010100","001000"}));
        System.out.println(N.numberOfBeams(new String[]{"000","111","000"}));
    }
}
