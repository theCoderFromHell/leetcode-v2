package interviews.impl;

import interviews.CustomerConfig;
import interviews.RateLimiter;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class TokenBucketRateLimiter implements RateLimiter {

    int refreshPeriod = 1; //in seconds
    int tokensPerPeriod;

    HashMap<Integer, CustomerConfig> customerConfigStore;

    public TokenBucketRateLimiter(int tokensPerPeriod) {
        this.customerConfigStore = new HashMap<>();
        this.tokensPerPeriod = tokensPerPeriod;

    }

    public void addCustomer (int customerId, int capacity) {
        CustomerConfig customerConfig =
                new CustomerConfig(new LinkedBlockingQueue(), capacity, capacity, System.currentTimeMillis());
        customerConfigStore.put(customerId, customerConfig);
    }

    @Override
    public boolean rateLimit(int customerId) {
        if (!customerConfigStore.containsKey(customerId))
            return false;

        if (canBeAllowed(customerId))
            return true;
        return false;
    }

    private boolean canBeAllowed(int customerId) {
        CustomerConfig customerConfig = customerConfigStore.get(customerId);
        long lastUpdatedTime = customerConfig.getLastUpdatedTime();
        long currentTime  = System.currentTimeMillis();
        int newTokens = (int)((currentTime - lastUpdatedTime)/1000)*tokensPerPeriod;
        customerConfig.setAvailableTokens(Math.min(customerConfig.getTotalCapacity(),
                customerConfig.getAvailableTokens() + newTokens));
        customerConfig.setAvailableTokens(customerConfig.getAvailableTokens() - 1);
        customerConfig.setLastUpdatedTime(currentTime);
        if (customerConfig.getAvailableTokens() >= 0)
            return true;
        return false;


    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucketRateLimiter tokenBucketRateLimiter = new TokenBucketRateLimiter(5);
        tokenBucketRateLimiter.addCustomer(1, 10);
        for (int i = 0; i < 15; i++) {
            System.out.println(tokenBucketRateLimiter.rateLimit(1));
        }
        Thread.sleep(1000);
        for (int i = 0; i < 15; i++) {
            System.out.println(tokenBucketRateLimiter.rateLimit(1));
        }
    }
}
