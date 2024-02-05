package medium;

public class ReverseInteger {
    public int reverse(int x) {
        if ((x >= 0 && x <= 9) || (-x >= 0 && -x <= 9))
            return x;
        int sign = (x < 0) ? -1 : 1;
        x = Math.abs(x);
        int result = 0;
        while (x > 0) {
            int digit = x % 10;
            x /= 10;
            if (result > Integer.MAX_VALUE/10)
                return 0;
            if(result == Integer.MAX_VALUE/10) {
                if (sign == 1 && digit > 7)
                    return 0;
                if (sign == -1 && digit > 8)
                    return 0;
            }
            result = 10*result + digit;
        }
        return sign*result;
    }
}
