package medium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class CarFleet {
    public int carFleet(int target, int[] position, int[] speed) {
        int N = position.length;
        List<PositionSpeed> cars = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            cars.add(new PositionSpeed(position[i], speed[i], target));
        }
        cars.sort(Comparator.comparingInt(PositionSpeed::getPosition).reversed());
        Stack<PositionSpeed> stack = new Stack<>();
        for (PositionSpeed p : cars) {
            if (!stack.empty() && p.time <= stack.peek().time)
                continue;
            stack.push(p);
        }
        return stack.size();
    }

    class PositionSpeed {
        int position;
        int speed;
        double time;

        public PositionSpeed(int position, int speed, int target) {
            this.position = position;
            this.speed = speed;
            this.time = (double) (target - position) / speed;
        }

        public int getPosition() {
            return position;
        }

        @Override
        public String toString() {
            return "PositionSpeed{" +
                    "position=" + position +
                    ", speed=" + speed +
                    ", time=" + time +
                    '}';
        }
    }
}
