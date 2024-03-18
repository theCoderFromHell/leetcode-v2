package medium;

import java.util.*;

public class SnakeGame {
    int width;
    int height;
    Queue<String> food;
    LinkedHashSet<String> occupied;
    int score;
    boolean gameOver;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = new LinkedList<>();
        this.occupied = new LinkedHashSet<>();
        this.score = 0;
        this.gameOver = false;
        occupied.add("0-0");
        for (int[] foodCell : food)
            this.food.add(foodCell[0] + "-" + foodCell[1]);
    }

    public int move(String direction) {
        if (this.gameOver)
            return -1;
        String[] coordinates = occupied.getFirst().split("-");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        switch (direction) {
            case "L" :
                y--;
                break;
            case "R" :
                y++;
                break;
            case "U" :
                x--;
                break;
            case "D" :
                x++;
                break;
        }
        if (x < 0 || y < 0 || x >= height || y >= width)
            return -1;
        if (!food.isEmpty() && (x + "-" + y).equals(food.peek())) {
            score++;
            food.poll();
        } else
            occupied.removeLast();
        if (!occupied.isEmpty() && occupied.contains(x + "-" + y))
            return -1;
        occupied.addFirst(x + "-" + y);
        return score;
    }

    public static void main(String[] args) {
        SnakeGame game = new SnakeGame(3, 3, new int[][]{{2,0},{0,0},{0,2},{2,2}});
        System.out.println(game.move("D"));
        System.out.println(game.move("D"));
        System.out.println(game.move("R"));
        System.out.println(game.move("U"));
        System.out.println(game.move("U"));
        System.out.println(game.move("L"));
        System.out.println(game.move("D"));
        System.out.println(game.move("R"));
        System.out.println(game.move("R"));
        System.out.println(game.move("U"));
        System.out.println(game.move("L"));
        System.out.println(game.move("D"));
    }

    class Cell {
        int x;
        int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */