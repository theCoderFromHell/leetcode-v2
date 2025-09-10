package medium;

public class MinimumAmountOfTimeToCollectGarbage {
    public int garbageCollection(String[] garbage, int[] travel) {
        int size = garbage.length;
        int[] travelCost = new int[3];
        int[] collectionCost = new int[3];
        int currTravel = 0;
        for (int i = 0; i < size; i++) {
            if (i > 0)
                currTravel += travel[i-1];
            int[] types = findTypes(garbage[i]);
            for (int j = 0; j < 3; j++) {
                collectionCost[j] += types[j];
                if (types[j] > 0)
                    travelCost[j] = currTravel;
            }
        }
        int result = 0;
        for (int i = 0; i < 3; i++)
            result += (travelCost[i] + collectionCost[i]);
        return result;
    }

    private int[] findTypes(String garbage) {
        int size = garbage.length();
        int[] types = new int[3];
        for (int i = 0; i < size; i++) {
            switch (garbage.charAt(i)) {
                case 'M' :
                   types[0]++;
                   break;
                case 'P' :
                    types[1]++;
                    break;
                case 'G' :
                    types[2]++;
                    break;
            }
        }
        return types;
    }

    public static void main(String[] args) {
        MinimumAmountOfTimeToCollectGarbage M = new MinimumAmountOfTimeToCollectGarbage();
        System.out.println(M.garbageCollection(new String[]{"G","P","GP","GG"}, new int[]{2,4,3}));
        System.out.println(M.garbageCollection(new String[]{"MMM","PGM","GP"}, new int[]{3,10}));
    }
}
