package medium;

public class MaxDifferenceYouCanGetFromChangingAnInteger {
    public int maxDiff(int num) {
        int minimum, maximum;
        StringBuilder value = new StringBuilder(String.valueOf(num));
        int size = value.length();
        for (int i = 0; i < size; i++) {
            if (i == 0 && value.charAt(i) != '1') {
                modify(value, size, i, '1');
                break;
            }
            if (i > 0 && value.charAt(i) != '0' && value.charAt(i) != '1') {
                modify(value, size, i, '0');
                break;
            }
        }
        minimum = Integer.parseInt(value.toString());
        value = new StringBuilder(String.valueOf(num));
        for (int i = 0; i < size; i++) {
            if (value.charAt(i) != '9') {
                modify(value, size, i, '9');
                break;
            }
        }
        maximum = Integer.parseInt(value.toString());
        return maximum - minimum;
    }

    private void modify(StringBuilder value, int size, int index, char update) {
        char c = value.charAt(index);
        for (int i = 0; i < size; i++) {
            if (value.charAt(i) == c)
                value.setCharAt(i, update);
        }
    }

    public static void main(String[] args) {
        MaxDifferenceYouCanGetFromChangingAnInteger M = new MaxDifferenceYouCanGetFromChangingAnInteger();
        System.out.println(M.maxDiff(555));
        System.out.println(M.maxDiff(9));
        System.out.println(M.maxDiff(1555));
        System.out.println(M.maxDiff(1255));
    }
}
