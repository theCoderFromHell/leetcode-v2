package medium;

import common.ListNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinkedListRandomNode {

    class Solution {
        ListNode head;
        List<Integer> values;

        public Solution(ListNode head) {
            this.head = head;
            this.values = toList(head);
        }

        public int getRandom() {
            int size = values.size();
            return values.get(new Random().nextInt(0, size));
        }

        private List<Integer> toList(ListNode head) {
            List<Integer> values = new ArrayList<>();
            while (head != null) {
                values.add(head.val);
                head = head.next;
            }
            return values;
        }
    }
}
