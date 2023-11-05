package interviews;

public interface RateLimiter {

    boolean rateLimit(int customerId);
}
