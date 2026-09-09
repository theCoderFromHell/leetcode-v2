package medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

// https://leetcode.com/problems/design-order-management-system/
public class DesignOrderManagementSystem {

    static class OrderManagementSystem {
        HashMap<String, HashSet<Integer>> orderTypePriceMap;
        HashMap<Integer, String> orderIdMap;
        public OrderManagementSystem() {
            this.orderTypePriceMap = new HashMap<>();
            this.orderIdMap = new HashMap<>();
        }

        public void addOrder(int orderId, String orderType, int price) {
            String id = orderType + "@" + price;
            orderIdMap.put(orderId, id);
            HashSet<Integer> orders = orderTypePriceMap.getOrDefault(id, new HashSet<>());
            orders.add(orderId);
            orderTypePriceMap.put(id, orders);
        }

        public void modifyOrder(int orderId, int newPrice) {
            String oldId = orderIdMap.get(orderId);
            String newId = oldId.split("@")[0] + "@" + newPrice;
            orderIdMap.put(orderId, newId);

            HashSet<Integer> orders = orderTypePriceMap.getOrDefault(oldId, new HashSet<>());
            orders.remove(orderId);
            orderTypePriceMap.put(oldId, orders);
            orders = orderTypePriceMap.getOrDefault(newId, new HashSet<>());
            orders.add(orderId);
            orderTypePriceMap.put(newId, orders);
        }

        public void cancelOrder(int orderId) {
            String oldId = orderIdMap.get(orderId);
            orderIdMap.remove(orderId);

            HashSet<Integer> orders = orderTypePriceMap.getOrDefault(oldId, new HashSet<>());
            orders.remove(orderId);
            orderTypePriceMap.put(oldId, orders);
        }

        public int[] getOrdersAtPrice(String orderType, int price) {
            String id = orderType + "@" + price;
            HashSet<Integer> orders = orderTypePriceMap.getOrDefault(id, new HashSet<>());
            int[] result = new int[orders.size()];
            int index = 0;
            for (int order : orders)
                result[index++] = order;
            return result;
        }
    }

    /*
     * Revision Note — Design Order Management System (Medium)
     * Pattern: Bucketed hash index + reverse index
     * Key Insight: Lookups are by (orderType, price) but mutations arrive keyed by
     *              orderId. Keep TWO maps: a forward index "type@price" -> Set<orderId>
     *              for O(k) queries, and a reverse index orderId -> "type@price" so
     *              modify/cancel can locate the bucket in O(1) instead of scanning.
     * Gotchas:
     *   - modifyOrder to the SAME price must still work: remove-then-add on the same
     *     set object is self-cancelling, which is the correct no-op.
     *   - The composite key works only because orderType ("buy"/"sell") never contains
     *     the '@' separator — otherwise split() would recover the wrong type.
     *   - getOrDefault(k, new HashSet<>()) + put(k, s) is redundant when k exists; the
     *     set is mutable and already in the map. computeIfAbsent expresses this better.
     *   - Emptied buckets are never pruned; harmless here (<=2000 ops) but a leak at scale.
     * Template:
     *   add:    key = type + "@" + price; reverse[id] = key; forward[key].add(id)
     *   modify: old = reverse[id]; new = type(old) + "@" + newPrice
     *           forward[old].remove(id); forward[new].add(id); reverse[id] = new
     *   cancel: old = reverse.remove(id); forward[old].remove(id)
     *   query:  copy forward[type + "@" + price] into int[]
     */
    public static void main(String[] args) {
        OrderManagementSystem O = new OrderManagementSystem();
        O.addOrder(1, "buy", 1);
        O.addOrder(2, "buy", 1);
        O.addOrder(3, "sell", 2);
        System.out.println("Test 1: " + sorted(O.getOrdersAtPrice("buy", 1)) + " (Expected: [1, 2])");

        O.modifyOrder(1, 3);
        O.modifyOrder(2, 1);
        System.out.println("Test 2: " + sorted(O.getOrdersAtPrice("buy", 1)) + " (Expected: [2])");
        System.out.println("Test 3: " + sorted(O.getOrdersAtPrice("buy", 3)) + " (Expected: [1])");
        System.out.println("Test 4: " + sorted(O.getOrdersAtPrice("sell", 2)) + " (Expected: [3])");

        O.cancelOrder(3);
        System.out.println("Test 5: " + sorted(O.getOrdersAtPrice("sell", 2)) + " (Expected: [])");
        System.out.println("Test 6: " + sorted(O.getOrdersAtPrice("buy", 999)) + " (Expected: [])");

        // modify to the same price must be a no-op, not a vanishing order
        OrderManagementSystem O2 = new OrderManagementSystem();
        O2.addOrder(10, "buy", 5);
        O2.modifyOrder(10, 5);
        System.out.println("Test 7: " + sorted(O2.getOrdersAtPrice("buy", 5)) + " (Expected: [10])");

        // same price, different types must not collide
        OrderManagementSystem O3 = new OrderManagementSystem();
        O3.addOrder(20, "buy", 7);
        O3.addOrder(21, "sell", 7);
        System.out.println("Test 8: " + sorted(O3.getOrdersAtPrice("buy", 7)) + " (Expected: [20])");
        System.out.println("Test 9: " + sorted(O3.getOrdersAtPrice("sell", 7)) + " (Expected: [21])");
    }

    // getOrdersAtPrice has no ordering guarantee — sort so tests compare deterministically
    private static String sorted(int[] arr) {
        Arrays.sort(arr);
        return Arrays.toString(arr);
    }
}
