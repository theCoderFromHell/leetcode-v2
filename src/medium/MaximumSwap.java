package medium;

public class MaximumSwap {
    public int maximumSwap(int num) {
        if (num <= 11)
            return num;
        char[] values = String.valueOf(num).toCharArray();
        int size = values.length;
        for (int i = 0; i < size; i++) {
            int maxIndex = i;
            for (int j = i+1; j < size; j++) {
                if (values[j] - '0' >= values[maxIndex] - '0')
                    maxIndex = j;
            }
            if (values[maxIndex] - '0' > values[i] - '0') {
                char temp = values[i];
                values[i] = values[maxIndex];
                values[maxIndex] = temp;
                break;
            }
        }
        return Integer.parseInt(String.copyValueOf(values));
    }

    public static void main(String[] args) {
        MaximumSwap M = new MaximumSwap();
        System.out.println(M.maximumSwap(2736));
        System.out.println(M.maximumSwap(9973));
        System.out.println(M.maximumSwap(1234));
        System.out.println(M.maximumSwap(1993));
    }
}
