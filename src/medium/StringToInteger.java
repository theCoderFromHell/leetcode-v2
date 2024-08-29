package medium;

public class StringToInteger {
    public int myAtoi(String s) {
        if (s == null || s.isEmpty())
            return 0;
        int N = s.length();
        int index = 0;
        int result = 0;
        int sign = 1;
        while (index < N && s.charAt(index) == ' ')
            index++;
        if (index < N && s.charAt(index) == '-') {
            index++;
            sign = -1;
        }
        else if (index < N && s.charAt(index) == '+') {
            index++;
            sign = 1;
        }
        while (index < N && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';

            if ((result > Integer.MAX_VALUE/10) || (result == Integer.MAX_VALUE/10 && digit > Integer.MAX_VALUE % 10)) {
                return (sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            }

            result = 10 * result + digit;
            index++;
        }
        return sign * result;
    }

    public static void main(String[] args) {
        StringToInteger S = new StringToInteger();
        System.out.println(S.myAtoi("-5254"));
        System.out.println(S.myAtoi("+87"));
        System.out.println(S.myAtoi("7492928"));
    }
}
