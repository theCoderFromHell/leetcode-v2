# Facebook News Feed

**Shape:** read-heavy / fan-out · **Core tension:** fan-out on write vs fan-out on read

## 1. Functional
- Create posts
- Follow users
- View feed in reverse chronological order
- Paginate

## 2. Non-Functional
- Availability over consistency — **1 minute of staleness is acceptable**
- Response **< 500ms**
- **2B users**, unlimited follows

## 3. Core Entities
User · Follow (uni-directional) · Post

## 4. API
```
POST /posts
PUT  /users/{id}/follow                        (idempotent)
GET  /feed?pageSize={size}&cursor={timestamp}
```

## 5. High-Level Design
API Gateway → Post Service → DynamoDB (posts) · Follow Service → DynamoDB (follows, with GSI) · Feed Service queries both and assembles reverse-chronologically.

## 6. Deep Dives

| Problem | Good | **Great** |
|---|---|---|
| User follows many accounts | `PrecomputedFeed` table — 200 posts/user × 2B users = **4TB** | **Hybrid** — precompute for most, fall back to on-read for high-follow accounts |
| High-follower posts cause write fan-out | Async SQS workers updating feeds | **Hybrid** — exclude high-follower accounts from precomputation, merge their recent posts at read time |
| Viral post creates a hot key | Distributed Redis cache — *which creates a new hot key* | **Replicated cache instances** spreading traffic across N nodes without sharding |

## Key Insight
**Fan-out on write** (precompute) vs **fan-out on read** (assemble on request) is the fundamental tradeoff. The correct answer is *both*, selected per account based on follower count. Committing to one uniformly is the mid-level answer.

## Common Mistakes
- Jumping to deep dives before the functional design is complete
- Assuming uniform load instead of designing for a **power-law distribution**
- Over-engineering when a product constraint solves it — Facebook's **5,000-friend limit** bounds the problem
