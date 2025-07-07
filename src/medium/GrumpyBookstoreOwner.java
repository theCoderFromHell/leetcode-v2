package medium;

public class GrumpyBookstoreOwner {
    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int size = customers.length;
        int alreadySatisfied = 0;
        for (int i = 0; i < size; i++)
            alreadySatisfied += (grumpy[i] == 1 ? 0 : customers[i]);
        int currWindowSatisfied = 0;
        for (int i = 0; i < minutes; i++) {
            if (grumpy[i] == 1)
                currWindowSatisfied += customers[i];
        }
        int maxSatisfied = currWindowSatisfied;
        int start = 0;
        int end = minutes;
        while (end < size) {
            if (grumpy[start] == 1)
                currWindowSatisfied -= customers[start];
            start++;
            if (grumpy[end] == 1)
                currWindowSatisfied += customers[end];
            end++;
            maxSatisfied = Math.max(maxSatisfied, currWindowSatisfied);
        }
        return alreadySatisfied + maxSatisfied;
    }

    public static void main(String[] args) {
        GrumpyBookstoreOwner G = new GrumpyBookstoreOwner();
        System.out.println(G.maxSatisfied(new int[]{1,0,1,2,1,1,7,5}, new int[]{0,1,0,1,0,1,0,1}, 3));
        System.out.println(G.maxSatisfied(new int[]{1}, new int[]{0}, 1));
    }
}
