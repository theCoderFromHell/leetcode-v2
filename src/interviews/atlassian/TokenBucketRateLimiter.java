package interviews.atlassian;

public class TokenBucketRateLimiter implements RateLimiter {
    int maxBucketSize;
    long nextRefillTime;
    int currBucketSize;
    int refillInterval;

    public TokenBucketRateLimiter(int maxBucketSize, int refillInterval) {
        this.maxBucketSize = maxBucketSize;
        this.nextRefillTime = System.currentTimeMillis() + refillInterval;
        this.currBucketSize = maxBucketSize;
        this.refillInterval = refillInterval;
    }

    @Override
    public boolean serve() {
        refill();
        if (this.currBucketSize > 0) {
            this.currBucketSize--;
            return true;
        }
        return false;
    }

    private void refill() {
        long currTime = System.currentTimeMillis();
        if (currTime >= this.nextRefillTime) {
            this.currBucketSize = this.maxBucketSize;
            this.nextRefillTime = currTime + this.refillInterval;
        }
    }
}
