package medium;

import java.util.*;

public class DisplayTableOfFoodOrdersInARestaurant {
    public List<List<String>> displayTable(List<List<String>> orders) {
        TreeMap<Integer, TreeMap<String, Integer>> displayMap = new TreeMap<>();
        int size = orders.size();
        for (int i = 0; i < size; i++) {
            Integer table = Integer.parseInt(orders.get(i).get(1));
            String foodItem = orders.get(i).get(2);
            TreeMap<String, Integer> tableMap = displayMap.getOrDefault(table, new TreeMap<>());
            tableMap.put(foodItem, tableMap.getOrDefault(foodItem, 0) + 1);
            displayMap.put(table, tableMap);
        }
        List<List<String>> result = new ArrayList<>();
        TreeSet<String> uniqueFoodItems = new TreeSet<>();
        for (Integer table : displayMap.keySet()) {
            for (String foodItem : displayMap.get(table).keySet())
                uniqueFoodItems.add(foodItem);
        }
        result.add(new ArrayList<>());
        result.getFirst().add("Table");
        for (String foodItems : uniqueFoodItems)
            result.getFirst().add(foodItems);
        int index = 0;
        for (Integer table : displayMap.keySet()) {
            result.add(new ArrayList<>());
            index++;
            result.get(index).add(String.valueOf(table));
            for (String foodItems : uniqueFoodItems)
                result.get(index).add(String.valueOf(displayMap.get(table).getOrDefault(foodItems, 0)));
        }
        return result;
    }

    public static void main(String[] args) {
        DisplayTableOfFoodOrdersInARestaurant D = new DisplayTableOfFoodOrdersInARestaurant();
        System.out.println(D.displayTable(List.of(List.of("David","3","Ceviche"),List.of("Corina","10","Beef Burrito"),List.of("David","3","Fried Chicken"),List.of("Carla","5","Water"),List.of("Carla","5","Ceviche"),List.of("Rous","3","Ceviche"))));
        System.out.println(D.displayTable(List.of(List.of("James","12","Fried Chicken"),List.of("Ratesh","12","Fried Chicken"),List.of("Amadeus","12","Fried Chicken"),List.of("Adam","1","Canadian Waffles"),List.of("Brianna","1","Canadian Waffles"))));
        System.out.println(D.displayTable(List.of(List.of("Laura","2","Bean Burrito"),List.of("Jhon","2","Beef Burrito"),List.of("Melissa","2","Soda"))));
        System.out.println(D.displayTable(List.of(List.of("Alice","1","Pizza"),List.of("Bob","2","Pizza"),List.of("Charlie","3","Pizza"))));
        System.out.println(D.displayTable(List.of(List.of("John","5","Salad"),List.of("Jane","5","Salad"),List.of("John","5","Pizza"),List.of("Jane","5","Water"))));
    }
}
