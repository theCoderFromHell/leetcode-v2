# Networking Essentials

## Layers That Matter

| Layer | Protocols | Concern |
|---|---|---|
| **L3 Network** | IP | Routing, addressing, best-effort packet delivery |
| **L4 Transport** | TCP, UDP, QUIC | End-to-end delivery guarantees |
| **L7 Application** | DNS, HTTP, WebSocket, WebRTC | Application abstractions |

## Transport: TCP vs UDP

**TCP — the default.** Connection-oriented, three-way handshake, ordered and error-checked delivery, flow and congestion control. Interviewers assume you're using it; it usually doesn't need mentioning at all.

**UDP — justify it.** Connectionless, no delivery or ordering guarantees, 8-byte header. Roughly **1–10ms faster** than TCP for simple operations. Use when speed beats reliability: gaming, VoIP, live streaming. Caveat: browsers have no widespread UDP support outside WebRTC, so browser clients need a fallback.

## Application Protocols

| Protocol | Use when | Avoid when |
|---|---|---|
| **HTTP/REST** | Default for public APIs | Rarely wrong |
| **GraphQL** | Client data needs are genuinely flexible/uncertain | Fixed requirements (most interviews) — adds N+1 complexity |
| **gRPC** | Internal service-to-service, performance-critical | Public APIs — binary, poor browser/tooling support |
| **SSE** | Server→client push only (live scores, auction bids) | Bidirectional needs |
| **WebSockets** | True bidirectional realtime (chat, collaboration) | Anything one-directional |
| **WebRTC** | Audio/video calling, peer-to-peer | Almost everything else |

**gRPC numbers:** Protocol Buffers ≈ 15 bytes vs ≈ 40 bytes for equivalent JSON; benchmarks show up to **10x throughput** over REST.

**WebSocket warning:** "Launching into a WebSocket implementation without justifying why they are needed is a great way to get a thumbs down from your interviewer." They carry real cost — stateful connections at scale, plus firewall/proxy/load-balancer support.

**SSE mechanics:** Single HTTP connection, server pushes chunked messages. Client `EventSource` auto-reconnects with message-ID tracking. Watch for networks batching responses and breaking stream semantics.

**WebRTC needs three servers:** signaling (connection coordination), STUN (NAT traversal via hole punching), TURN (relay fallback). It is "an absolute pain to get right" — confine it to audio/video.

## Load Balancing

**Client-side.** Client queries a service registry and picks the server itself, removing a hop. Redis Cluster (clients hash keys locally) and DNS round-robin work this way. Only viable with few controlled clients, or when slow propagation via TTL is acceptable.

**L4 (transport).** Minimal inspection, connection-level routing, keeps persistent TCP connections pinned to a backend. **Required for WebSockets.** Optimized for raw throughput.

**L7 (application).** Inspects URLs, headers, cookies; can route `/api` separately from page requests. More CPU-intensive. Default for HTTP.

| Algorithm | Use for |
|---|---|
| Round robin / random | Stateless apps |
| Least connections | Persistent connections (SSE, WebSockets) |
| Least response time | Heterogeneous backends |
| IP hash | Session stickiness |

**Health checks:** TCP checks confirm connectivity; L7 checks verify a 200 rather than a 5xx.

## Geography and Latency

Light travels ≈ **200,000 km/s** in fiber. New York↔London round trip (~5,600 km) has a **~56ms floor from physics alone**, before any processing. Quote this when someone proposes a synchronous cross-region call.

**CDN** — hundreds/thousands of edge locations caching static content; cuts latency and origin load.

**Regional partitioning** — partition data and services by geography so each region hits local databases. Uber bundles nearby cities into regions (Northeast US, Southwest US) with co-located servers and databases.

## Failure Handling

**Timeouts and retries.** Set an expected duration, time out beyond it, retry transient failures. The magic phrase is **"retry with exponential backoff"** — and always add **jitter** so retries don't synchronize into a jackhammer storm.

**Idempotency.** Required for safe retries on writes. Use an idempotency key (e.g. user ID + date); the server stores the key with the first result and replays it on retry rather than reprocessing. This is what prevents double-charges.

**Circuit breakers.** Monitor failure rate; trip when it crosses a threshold.
1. **Open** — fail immediately without calling the struggling service
2. **Half-open** — after a timeout, let one test request through
3. **Closed** — normal operation

Fails fast, sheds load from a sick dependency, self-heals, and stops cascading failure.

## Interview Defaults

- TCP transport, stateless HTTP
- REST for public APIs
- L7 load balancing for HTTP, **L4 for WebSockets**

**Earns points:** justifying UDP with a real latency requirement · circuit breakers on external calls · naming cascading-failure risk · regional partitioning · jitter on backoff.

**Loses points:** hyperoptimizing RPC protocol choice before handling actual bottlenecks. Premature optimization reads as book knowledge.
