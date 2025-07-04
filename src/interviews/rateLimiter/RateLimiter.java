package interviews.rateLimiter;

public interface RateLimiter {

    boolean rateLimit(int customerId);
}
