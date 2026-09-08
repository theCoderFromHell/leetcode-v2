# Facebook Live Comments

**Shape:** realtime broadcast · **Core tension:** requirements change shape at extreme scale

## 1. Functional
- Post comments on a live video
- See new comments in near-realtime
- See historical comments from before joining

## 2. Non-Functional
- Millions of concurrent videos, **thousands of comments/sec per stream**
- Availability over consistency
- **< 200ms** end-to-end under typical conditions

## 3. Core Entities
User · Live Video (owned by another team) · Comment

## 4. API
```
POST /comments/:liveVideoId
GET  /comments/:liveVideoId?cursor={id}&pageSize=10&sort=desc
```

## 5. High-Level Design
Comment Management Service writes to DynamoDB. **Realtime Messaging Servers hold SSE connections** to viewers. New comments fan out via pub/sub to everyone watching that video.

## 6. Deep Dives

**Realtime broadcast — why SSE**
- Polling: rejected, inefficient
- WebSockets: **rejected because the read/write ratio is unbalanced** — many readers, few writers
- **SSE chosen**: "the infrequent comment creation can use standard HTTP POST requests, while the frequent reads benefit from SSE's efficient one-way streaming"

**Horizontal scaling** — naive pub/sub (every server processes every comment) replaced by **partitioned pub/sub** using consistent hashing and **L7 load balancing by `liveVideoId`**. Alternative: a dispatcher service centralizing routing.

**Mega-streams (100k+ viewers, 1000+ comments/sec)** — **sampling** cuts volume. Or a **CDN snapshot** approach: shift to pull-based polling every second, accepting 1–2s latency to reuse existing infrastructure.

**Disconnections** — SSE **`Last-Event-ID`** header enables automatic replay. Bound the replay window (last 5 min) so reconnecting users aren't flooded; a shared Redis cache allows catch-up across server changes.

## Key Insight
**Requirements themselves change at extreme scale.** A mega-stream cannot meaningfully show every comment — no human reads 1000/sec. So the optimum shifts from low-latency delivery of everything to *representative sampling*. Recognizing when a requirement stops making sense is a senior signal.

## Common Mistakes
- Starting complex instead of validating the simple approach first
- Assuming single-server design scales horizontally with no coordination strategy
- **Overestimating the need for 100% delivery** when human perception caps what's useful
