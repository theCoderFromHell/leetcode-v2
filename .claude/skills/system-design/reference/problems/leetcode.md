# LeetCode

**Shape:** untrusted execution · **Core tension:** running hostile code safely

## 1. Functional
- View problem list (paginated)
- View a problem with language-specific code stubs
- Submit a solution, get feedback
- View live competition leaderboard

## 2. Non-Functional
- Submission results within **5 seconds**
- **100,000 concurrent** competition users
- Availability over consistency
- **Code isolation and security**

## 3. Core Entities
Problem (statement, test cases, stubs per language) · Submission (code + results) · Leaderboard

## 4. API
```
GET  /problems?page=1&limit=100
GET  /problems/:id?language=python
POST /problems/:id/submit
GET  /leaderboard/:competitionId?page=1
```

## 5. High-Level Design
API Server → job queue (SQS-style) → isolated worker containers → results to DB + **Redis sorted set** for the leaderboard. Client polls a status endpoint.

## 6. Deep Dives

| Challenge | Solution |
|---|---|
| **Security / isolation** | Docker containers: read-only filesystem, CPU/memory bounds, no network, **seccomp syscall filtering**, explicit **5-second timeout** |
| **Leaderboard efficiency** | Redis sorted sets updated on submission; clients poll every **5 seconds** instead of querying the DB |
| **100k concurrent users** | Auto-scaling groups; queue buffers submission spikes; retry on container failure |
| **Cross-language testing** | **One standardized test-case serialization** (e.g. arrays for trees) + per-language deserializers in each container |

## Key Insight
> "Running user code directly on servers is catastrophic; containerization balances isolation, efficiency, and security while queuing prevents overwhelming workers during competitions."

## Common Mistakes
- Running untrusted code **in the API server process**
- Querying the whole submissions table on every leaderboard request
- **Over-engineering with WebSockets** when 5-second polling is sufficient — this is a rare case where the simple answer is correct and reaching for realtime costs signal
- Writing language-specific test cases per problem instead of one serialization format
