package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class AsteroidCollision {
    public static int[] asteroidCollision(int[] asteroids) {
        int N = asteroids.length;
        if (N == 1)
            return asteroids;
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            if (stack.empty() || asteroid > 0 || stack.peek() < 0)
                stack.add(asteroid);
            else {
                boolean destroyed = false;
                while (!destroyed && !stack.empty() && stack.peek() > 0) {
                    if (stack.peek() == -asteroid) {
                        stack.pop();
                        destroyed = true;
                    }
                    else if (stack.peek() < -asteroid)
                        stack.pop();
                    else
                        destroyed = true;
                }
                if (!destroyed)
                    stack.add(asteroid);
            }
        }
        if (stack.empty())
            return new int[]{};
        int[] result = new int[stack.size()];
        int i = 0;
        for (int asteroid : stack)
            result[i++] = asteroid;
        return result;
    }

    public static void main(String[] args) {
    }
}
