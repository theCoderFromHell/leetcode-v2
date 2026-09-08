# Distributed Rate Limiter

**Shape:** infrastructure · **Core tension:** a shared counter that must be fast and atomic

## 1. Functional
- Identify clients by user ID, IP, or API key
- Limit requests per configurable rule (e.g. 100/minute)
- Reject excess with **HTTP 429** plus helpful headers

## 2. Non-Functional
- **< 10ms** per request check
- Highly available; **eventual consistency is acceptable**
- **1M requests/sec** across **100M DAU**

## 3. Core Entities
Rule (policy) · Client (user / IP / API key) · Request

## 4. Interface
```
isRequestAllowed(clientId, ruleId)
  → { passes: boolean, remaining: number, resetTime: timestamp }
```

## 5. High-Level Design
Rate limiter sits **at the API Gateway** — the edge — inspecting every request before it goes downstream.

**Token bucket:** each client owns a bucket; tokens refill at a steady rate; each request consumes one. State (token count + last refill timestamp) lives in **Redis**, mutated through **Lua scripts for atomicity**.

## 6. Deep Dives

| Challenge | Solution |
|---|---|
| 1M req/sec | Redis **sharding with consistent hashing** across ~10 shards (~100k ops/sec each) |
| High availability | Master-replica with automatic failover; **fail-closed** during outage |
| Latency | Connection pooling; geographically distributed Redis clusters |
| Hot keys (viral spikes) | Client-side rate limiting, auto-block abusive clients, DDoS protection |
| Dynamic rules | Push-based config via **ZooKeeper** for realtime propagation to all gateways |

## Key Insight
**Placement determines functionality.** At the API Gateway you get centralized enforcement without per-service latency. And atomic Lua operations in Redis solve the distributed-counter problem that no in-process approach can.

## Common Mistakes
- **Local state on each gateway** — coordination breaks immediately
- **Fixed window counters** — boundary effects let clients burst 2x at window edges. Token bucket or sliding window instead
- **Failing open during a Redis outage** — cascades into collapse exactly when traffic is spiking
