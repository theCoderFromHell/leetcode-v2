package interviews.atlassian;

public class TokenBucketTester {

    public static void main(String[] args) {
        int maxBucketSize = 10;
        int interval = 1000;
        RateLimiter tokenBucket = new TokenBucketRateLimiter(maxBucketSize, interval);
        int testForSeconds  = 4;
        long finalTime = System.currentTimeMillis() + testForSeconds*1000;
        int count = 0;
        while (System.currentTimeMillis() < finalTime) {
            if (tokenBucket.serve()) {
                System.out.println("t = " + finalTime + " Request Served!!!");
                count++;
            }
            else
                System.out.println("t = " + finalTime + " Request Denied!!!");
        }
        System.out.println("Total requests served = " + count);
    }
}
