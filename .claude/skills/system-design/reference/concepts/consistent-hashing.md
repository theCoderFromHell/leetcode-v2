# Consistent Hashing

## The Problem It Solves

Naive modulo hashing — `database_id = hash(event_id) % num_databases` — remaps **nearly every key** when you add or remove a node. The resulting migration creates a severe load spike exactly when you were trying to add capacity.

## The Ring

- Hash space is circular, typically **0 to 2³² − 1**
- Nodes occupy fixed positions on the ring
- To place a key: hash it, walk **clockwise** to the first node
- Adding a node only moves keys between it and its clockwise neighbor

**The payoff:** ~**15%** of data moves versus ~**100%** with modulo. (The in-a-hurry page frames the same idea as ~10% vs ~90%; either figure makes the point.)

## Virtual Nodes

Rather than one ring position per server, hash variations — `DB1-vn1`, `DB1-vn2`, … — to scatter each server across many positions.

- A failed node's load spreads across **all** survivors, not just its clockwise neighbor
- A new node draws data from many existing nodes rather than one
- Rebalancing is smoother overall

## Hot Spots

1. **Read replicas** — most common; replicate the popular key across nodes
2. **Key-space salting** — append a random suffix (`taylor-swift-{0..9}`) to scatter one logical key
3. **Adaptive rebalancing** — monitor live traffic and migrate hot ranges. Complex; DynamoDB does this

**The distinction worth stating out loud:** virtual nodes fix **structural** imbalance (uneven key distribution). Replication fixes **workload** imbalance (uneven traffic to a key). They are not interchangeable, and naming the difference is a senior signal.

## In Production

Real systems pair consistent hashing with replication. DynamoDB replicates partitions across three AZs; Cassandra replicates to N consecutive ring nodes. Data moves only on planned membership changes, not routine failures.

**Notable exception:** Redis Cluster does *not* use a ring — it uses **fixed hash slots**, `CRC16(key) mod 16384`. Simpler to reason about, but rebalancing needs more coordination.

## When to Go Deep

**Deep** — designing a distributed cache, database, or message broker from scratch; infrastructure-focused interviews.

**Shallow** — you're using DynamoDB/Cassandra/a CDN. Just name that they use consistent hashing under the hood and move on. The point is demonstrating you know adding servers doesn't require a full redistribution.

## Talking Points, In Order

1. Why modulo hashing fails
2. The ring and clockwise lookup
3. Virtual nodes for even load
4. What happens on node add/failure
5. Structural vs workload imbalance
6. When an existing system already handles it for you
