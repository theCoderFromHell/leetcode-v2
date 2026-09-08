# Ticketmaster

**Shape:** contention · **Core tension:** consistency for booking, availability for browsing

## 1. Functional
- View events with seat maps
- Search events by keyword, date, location
- Book tickets **without double-booking**

## 2. Non-Functional
> "Prioritize availability for searching & viewing events, but prioritize consistency for booking events"

- **10M concurrent users** on a single event
- Search latency **< 500ms**
- **100:1** read/write ratio

## 3. Core Entities
Event · User · Performer · Venue (seat map) · Ticket (`available` / `reserved` / `booked`) · Booking (groups tickets + payment status)

## 4. API
```
GET  /events/:eventId                        → event + venue + tickets
GET  /events/search?keyword=&start=&end=     → events
POST /bookings/:eventId                      → bookingId
```

## 5. High-Level Design
API Gateway → **Event Service** (cached reads) · **Search Service** (Elasticsearch) · **Booking Service** (Postgres transactions). Redis holds distributed locks for reservations with a TTL.

## 6. Deep Dives

| Challenge | Solution |
|---|---|
| Double-booking | Redis distributed lock, **10-min TTL**; database OCC as fallback |
| High read load | Redis cache + load balancing + horizontal scaling |
| Popular-event UX | **Virtual waiting queue** using Redis sorted sets |
| Search latency | Elasticsearch inverted index, **CDC sync from Postgres** |
| Query caching | Elasticsearch request cache + CDN edge caching |

## Key Insight
> "Reservations living in Redis rather than the database" separates temporary holds from permanent state — expiration happens automatically, with no application-level cleanup.

## Common Mistakes
- **Long-running database locks** — strain resources, cause deadlocks
- **Cron jobs for expiration** — unpredictable lag
- Failing to invalidate the seat map cache when a lock expires

## Compare With Reality
Shopify solved this same problem differently in production — see `in-the-wild/shopify-inventory.md`. They rejected the Redis-hold approach precisely because Redis can't participate in the ledger transaction, and used **one row per reservable unit + `SELECT … FOR UPDATE SKIP LOCKED`** instead. Knowing both, and why they diverge, is a Strong Hire conversation.
