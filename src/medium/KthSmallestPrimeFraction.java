package medium;

import java.util.Arrays;
import java.util.PriorityQueue;

public class KthSmallestPrimeFraction {
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        int size = arr.length;
        PriorityQueue<PrimeFraction> pq = new PriorityQueue<>((o1, o2) -> o1.a * o2.b - o1.b * o2.a);
        for (int i = 0; i < size - 1; i++)
            pq.add(new PrimeFraction(arr[i], arr[size - 1], i, size - 1));
        int count = 0;
        while (!pq.isEmpty()) {
            PrimeFraction pf = pq.poll();
            count++;
            if (count == k)
                return new int[]{pf.a, pf.b};
            if (pf.bIndex > pf.aIndex + 1) {
                pq.add(new PrimeFraction(pf.a, arr[pf.bIndex - 1], pf.aIndex, pf.bIndex - 1));
            }
        }
        return new int[]{};
    }

    class PrimeFraction {
        int a;
        int b;
        int aIndex;
        int bIndex;

        public PrimeFraction(int a, int b, int aIndex, int bIndex) {
            this.a = a;
            this.b = b;
            this.aIndex = aIndex;
            this.bIndex = bIndex;
        }

        @Override
        public String toString() {
            return "PrimeFraction{" +
                    "a=" + a +
                    ", b=" + b +
                    ", aIndex=" + aIndex +
                    ", bIndex=" + bIndex +
                    '}';
        }
    }

    public static void main(String[] args) {
        KthSmallestPrimeFraction K = new KthSmallestPrimeFraction();
        System.out.println(Arrays.toString(K.kthSmallestPrimeFraction(new int[]{1, 2, 3, 5}, 3)));
        System.out.println(Arrays.toString(K.kthSmallestPrimeFraction(new int[]{1, 7}, 1)));
    }
}