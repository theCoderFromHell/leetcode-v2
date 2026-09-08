# Data Modeling

## Choosing a Database

**Relational (Postgres) — the default.** "Most of the time, the right answer is a relational database." Complex queries with joins, ACID where consistency is critical (payments, inventory). Start here unless requirements force otherwise.

**Document** — only if the schema genuinely changes often, which is rare in a scoped interview problem. Embeds related data to avoid joins; trades storage and update complexity for read speed.

**Key-value** — single-key lookups at maximum speed. Usually *paired* with SQL (Redis in front of Postgres), not a replacement. Supporting multiple access patterns means denormalizing across multiple keys.

**Wide-column** — massive write volume, time-series. Rows organized by partition key for sequential access. **Do not partition by time in write-heavy systems** — every current write lands on the newest partition.

**Graph** — rarely needed. Facebook models its social graph in MySQL. Reaching for a graph DB is a common interview mistake.

## Start From Three Requirements

1. **Data volume** — decides whether sharding is even on the table
2. **Access patterns** — drives most decisions; derive them from your API endpoints
3. **Consistency needs** — financial → ACID; feeds → eventual

## Keys and Relationships

- Primary keys are **system-generated IDs** (`user_id`, `post_id`), never business data like an email
- Foreign keys enforce referential integrity
- Enforce `NOT NULL` / `UNIQUE` at the database, not just the app
- Name things in domain language — users, posts, follows — not abstract nouns

## Indexing From Access Patterns

Every index should trace to a query. `GET /users/{id}/posts` needs an index on `posts.user_id`. Composite indexes like `(user_id, created_at)` serve range queries efficiently.

## Normalize First

Store each fact exactly once. Changing a user's email is then one update, not a scan across thousands of copies.

**Denormalize only when:**
- Analytics/reporting where data changes rarely
- Event snapshots or audit trails
- Read-heavy paths where consistency is genuinely secondary

**Better alternative:** keep the source normalized and put the denormalized shape in a **cache**. You get read speed without permanent schema complexity.

## Shard Key From Access Pattern

Shard by how you query. If most queries are "posts by user," shard by `user_id` — related data stays co-located and cross-shard queries stay rare.

**Anti-patterns:** time-range sharding under heavy writes (hot shard); cross-shard joins (design the key to prevent them). Treat the shard key as **permanent**.

## Interview Checklist

When you introduce a database, cover these six in order:

1. Database type, with justification
2. Columns per entity
3. Primary and foreign keys
4. Which columns are indexed
5. Any denormalization, and why
6. Shard key, if scale demands one

Tie every decision to a requirement: *"Since we need fast feeds and consistency isn't critical here, I'll denormalize like counts into posts."* Resist showing off with exotic database types — keep it functional and move the conversation forward.
