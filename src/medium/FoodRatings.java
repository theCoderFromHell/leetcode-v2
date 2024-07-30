package medium;

import java.util.*;

public class FoodRatings {
    HashMap<String, PriorityQueue<FoodWithRating>> best;
    HashMap<String, String> cuisineForFood;
    HashMap<String, Integer> ratingForFood;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        this.best = new HashMap<>();
        this.cuisineForFood = new HashMap<>();
        this.ratingForFood = new HashMap<>();
        for (int i = 0; i < foods.length; i++) {
            cuisineForFood.put(foods[i], cuisines[i]);
            ratingForFood.put(foods[i], ratings[i]);
            PriorityQueue<FoodWithRating> pq = best.getOrDefault(cuisines[i], new PriorityQueue<>((o1, o2) -> {
                if (o1.rating == o2.rating) {
                    return o1.food.compareTo(o2.food);
                }
                return o2.rating - o1.rating;
            }));
            pq.add(new FoodWithRating(foods[i], ratings[i]));
            best.put(cuisines[i], pq);
        }
    }

    public void changeRating(String food, int newRating) {
        ratingForFood.put(food, newRating);
        String cuisine = cuisineForFood.get(food);
        PriorityQueue<FoodWithRating> pq = best.get(cuisine);
        pq.add(new FoodWithRating(food, newRating));
        best.put(cuisine, pq);
    }

    public String highestRated(String cuisine) {
        FoodWithRating foodWithRating = best.get(cuisine).peek();
        while (foodWithRating.rating != ratingForFood.get(foodWithRating.food)) {
            best.get(cuisine).poll();
            foodWithRating = best.get(cuisine).peek();
        }
        return foodWithRating.food;
    }

    class FoodWithRating{
        String food;
        int rating;

        public FoodWithRating(String food, int rating) {
            this.food = food;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return "FoodWithRating{" +
                    "food='" + food + '\'' +
                    ", rating=" + rating +
                    '}';
        }

        public String getFood() {
            return food;
        }

        public int getRating() {
            return rating;
        }
    }

    public static void main(String[] args) {
        FoodRatings F = new FoodRatings(new String[]{"kimchi","miso","sushi","moussaka","ramen","bulgogi"},
                new String[]{"korean","japanese","japanese","greek","japanese","korean"},
                new int[]{9,12,8,15,14,7});
        System.out.println(F.highestRated("korean"));
        System.out.println(F.highestRated("japanese"));
        F.changeRating("sushi",16);
        System.out.println(F.highestRated("japanese"));
        F.changeRating("ramen",16);
        System.out.println(F.highestRated("japanese"));
    }
}