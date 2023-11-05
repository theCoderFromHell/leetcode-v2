package interviews;

import java.util.concurrent.BlockingQueue;

public class CustomerConfig {
    BlockingQueue queue;
    int availableTokens;
    int totalCapacity;
    long lastUpdatedTime;

    public CustomerConfig(BlockingQueue queue, int currentLoad, int totalCapacity, long lastUpdatedTime) {
        this.queue = queue;
        this.availableTokens = currentLoad;
        this.totalCapacity = totalCapacity;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "CustomerConfig{" +
                "queue=" + queue +
                ", currentLoad=" + availableTokens +
                ", totalCapacity=" + totalCapacity +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }

    public BlockingQueue getQueue() {
        return queue;
    }

    public int getAvailableTokens() {
        return availableTokens;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setQueue(BlockingQueue queue) {
        this.queue = queue;
    }

    public void setAvailableTokens(int availableTokens) {
        this.availableTokens = availableTokens;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public void setLastUpdatedTime(long lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}
