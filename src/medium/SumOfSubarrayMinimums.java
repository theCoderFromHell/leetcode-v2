package medium;
import java.util.Stack;

public class SumOfSubarrayMinimums {
    public static int sumSubarrayMins(int[] arr) {
        int N = arr.length;
        long MOD = 1000000007;
        Stack<Integer> stack = new Stack<>();
        long result = 0;
        for (int i = 0; i <= N; i++) {
            while (!stack.empty() && (i == N || arr[i] <= arr[stack.peek()])) {
                int top = stack.pop();
                int left = top - (stack.empty() ? -1 :stack.peek());
                int right = i - top;
                result += ((long) arr[top] * left * right)%MOD;
                result %= MOD;
            }
            stack.add(i);
        }
        return (int) result;
    }
    public static void main(String[] args) {
        System.out.println(sumSubarrayMins(new int[]{11,81,94,43,3}));
    }
}
