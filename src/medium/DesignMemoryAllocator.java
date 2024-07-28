package medium;

import java.util.Arrays;

public class DesignMemoryAllocator {
    int[] memory;
    int length;
    public DesignMemoryAllocator(int n) {
        this.memory = new int[n];
        this.length = n;
        Arrays.fill(memory, 0);
    }

    public int allocate(int size, int mID) {
        int startOfTheBlock = -1;
        for (int i = 0; i < length; i++) {
            if (memory[i] == 0 && i+size-1 < length && memory[i+size-1] == 0) {
                int count = 0;
                for (int j = i; j < i+size; j++) {
                    if (memory[j] == 0)
                        count++;
                }
                if (count == size) {
                    startOfTheBlock = i;
                    for (int j = i; j < i+size; j++) {
                        memory[j] = mID;
                    }
                    break;
                }
            }
        }
        return startOfTheBlock;
    }

    public int free(int mID) {
        int count  = 0;
        for (int i = 0; i < length; i++) {
            if (memory[i] == mID) {
                count++;
                memory[i] = 0;
            }
        }
        return count;
    }
}
