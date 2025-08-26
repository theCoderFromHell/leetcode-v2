package interviews.online.assessment.codility;

public class MaxDistanceBetweenFrogs {
    public int maxDistanceBetweenFrogs(int[] blocks) {
        int size = blocks.length;
        if (size == 0)
            return 0;
        int[] left = new int[size];
        int[] right = new int[size];

        left[0] = 0;
        for (int i = 1; i < size; i++) {
            left[i] = i;
            while (left[i] > 0 && blocks[left[i] - 1] >= blocks[i])
                left[i] = left[left[i] - 1];
        }

        right[size-1] = size-1;
        for (int i = size-2; i >= 0; i--) {
            right[i] = i;
            while (right[i] < size-1 && blocks[right[i] + 1] >= blocks[i])
                right[i] = right[right[i] + 1];
        }

        int maxDistance = 0;
        for (int i = 0; i < size; i++) {
            maxDistance = Math.max(maxDistance, right[i] - left[i] + 1);
        }

        return maxDistance;
    }
}
