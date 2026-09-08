# Uber

**Shape:** proximity + contention · **Core tension:** realtime geospatial at 2M writes/sec

## 1. Functional
- Fare estimation from pickup to destination
- Rider confirms a ride request
- Match drivers by proximity and availability
- Driver accepts/declines, then navigates

## 2. Non-Functional
- Match within **< 1 minute** or fail
- **Strong consistency** — never assign one driver twice
- **100k requests from the same location** at peak

## 3. Core Entities
Rider · Driver · Fare · Ride · Location (lat/long + timestamp)

## 4. API
```
POST  /fare               { pickup, destination }  → Fare
POST  /rides              { fareId }               → Ride
POST  /drivers/location   { lat, long }
PATCH /rides/:rideId      { accept | deny }        → Ride
```

## 5. High-Level Design
Clients → API Gateway → **Ride Service** (fare) · **Location Service** (driver positions) · **Ride Matching Service** (proximity) · **Notification Service** (push). Drivers stream location continuously; matching queries the nearest available.

## 6. Deep Dives

| Challenge | ❌ Bad | ✅ Solution |
|---|---|---|
| Location writes + proximity search | Direct DB writes, full scans | **Redis geospatial commands** with geohashing — sub-second queries |
| Overload from location pings | Fixed 5-second interval | **Adaptive intervals** based on speed/direction, computed client-side |
| Duplicate driver assignment | In-memory locks, manual timeouts | **Redis distributed lock, 10-second TTL**, auto-expiry |
| Dropped requests at peak | First-come-first-served | **Kafka queue** with offset commits; scale matching services horizontally |
| Driver doesn't respond | Delay queues + manual coordination | **Temporal / durable execution** handling retries and state |
| Latency and throughput | Vertical scaling | **Geo-sharded** services, databases, queues + read replicas; proximity queries never cross shards |

## Key Insight
**Redis geospatial indexing is the lynchpin** — it absorbs **2M location updates/sec** with millisecond proximity search, which is the system's tightest bottleneck. Everything else is arranged around it.

## Common Mistakes
- **Passing user IDs and timestamps in request bodies** — security risk; derive from the auth token
- Ignoring client-side optimization — adaptive ping intervals cut infrastructure load **~60%**
- **In-memory timeouts** that vanish across service restarts
- Single-region deployment for a geographically distributed user base
