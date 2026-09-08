# Tinder

**Shape:** proximity + consistency · **Core tension:** atomic mutual-swipe detection at scale

## 1. Functional
- Create profile with preferences (age range, interests, max distance)
- View a stack of potential matches within filters and distance
- Swipe right/left
- Get notified on a mutual match

## 2. Non-Functional
- **Strong consistency** for swiping — never miss a match
- **20M DAU**, ~100 swipes/user/day = **2B swipes/day**
- Stack loads in **< 300ms**
- **Never re-show** a previously swiped profile

## 3. Core Entities
User (profile + preferences) · Swipe · Match

## 4. API
```
POST /profile
GET  /feed?lat={}&long={}&distance={}
POST /swipe/{userId}    { decision: yes|no }
```

## 5. High-Level Design
API Gateway → Profile Service (relational DB) · Swipe Service (**Cassandra, partitioned by `swiping_user_id`**) · Push notifications (APNS/FCM). On swipe, check for the reciprocal swipe **in the same partition**; if mutual yes, notify.

## 6. Deep Dives

**Consistent, low-latency swiping**
- ❌ *Bad* — periodic polling loses immediate feedback
- ✅ *Good* — Cassandra lightweight transactions, limited to a single partition
- ✅ **Great** — sharded Cassandra with a **compound key placing both users of a pair in the same partition**, enabling an atomic transaction
- ✅ **Great alt** — Redis + Lua scripts for atomicity, Cassandra as durable backup. Trades RAM for guaranteed consistency

**Low-latency feed generation**
- ❌ *Bad* — realtime geospatial SQL; can't hit 300ms
- ✅ *Good* — Elasticsearch / geospatial index
- ✅ *Good* — precompute and cache feeds off-peak
- ✅ **Great** — **hybrid**: serve cached feed instantly, transition to Elasticsearch when an active user exhausts it; TTL **< 1h** to bound staleness

**Never re-showing profiles**
- ❌ *Bad* — query the swipe DB every feed request; fails under eventual consistency
- ✅ *Good* — client-side cache of recent swipes + backend contains-check
- ✅ **Great** — add a **Bloom filter** for users with large swipe histories; avoids expensive contains-checks with **no false negatives** (a false positive merely skips a profile, which is acceptable)

## Key Insight
Strong consistency *and* low latency for matching both come from **partitioning a user pair's swipes into the same shard**, which makes the check atomic. Feed latency is a separate problem solved by precomputation layered over indexed search.

## Common Mistakes
- Attempting cross-partition transactions in Cassandra — unsupported
- Realtime geospatial queries with no index
- Ignoring staleness in precomputed feeds
- Underestimating consistency lag and re-showing swiped profiles
