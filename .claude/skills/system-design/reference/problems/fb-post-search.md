# Facebook Post Search

**Shape:** search + write-heavy · **Core tension:** likes (100k/s) dwarf searches (10k/s)

## 1. Functional
- Create and like posts
- Search posts by keyword
- Sort by recency or like count
- *Out of scope:* fuzzy matching, personalization, privacy filters, ranking, media

## 2. Non-Functional
- Query latency **< 500ms** median
- **~10k searches/sec**
- New posts searchable within **< 1 minute**
- All posts discoverable (older may be slower)
- Storage **~3.6 PB** (10 years × 1kb/post)

## 3. Core Entities
User · Post (content, timestamp, implicit like count) · Like (**counted in aggregate, not stored individually**)

## 4. API
```
POST /posts
POST /likes
GET  /search?keyword=X&sort=recency|likes
```

## 5. High-Level Design
**Write:** Post Service → Ingestion Service → tokenize → write to **Redis inverted indexes** (a Creation index and a Likes index)
**Read:** API Gateway → Search Service → query inverted indexes → sorted postIds → hydrate post details

## 6. Deep Dives

| Challenge | Solution |
|---|---|
| High read volume | Distributed cache + CDN, **< 1 min TTL** on search results |
| Multi-keyword queries | Index **bigrams/shingles**, not just single words |
| High post write volume | Kafka stream + partitioned ingestion across workers |
| **Like volume (100k/sec)** | **Two-stage**: approximate counts in the index via **milestone-based writes**, then re-rank with fresh counts at query time |
| Storage (3.6 PB) | **Cap inverted index lists at 1–10k items**; move cold keywords to S3 |

## Key Insight
> "The system is write-heavy vs read-heavy."

Likes at **100k/sec** vastly outweigh searches at **10k/sec**. Noticing that inverts the whole design — like batching and approximate indexing become the core architecture, not optional polish. Most candidates classify this as a search problem and design for reads.

## Common Mistakes
- Full-text search on an unindexed database — scales linearly with data
- Fetching millions of post IDs for a common keyword and sorting in memory
- **Treating all writes equally** — like events need fundamentally different handling than post creation
