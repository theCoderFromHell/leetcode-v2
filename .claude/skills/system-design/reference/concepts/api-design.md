# API Design

## Protocol Choice

- **REST** — default. HTTP verbs on URL-identified resources, maps naturally to CRUD.
- **GraphQL** — single endpoint, client specifies exact data. Solves over/under-fetching, adds N+1 complexity. Only when flexibility is the stated problem.
- **gRPC** — binary over HTTP/2, service-to-service, type safety across polyglot services.

## Resource Modeling

- Core entities become resources: events, venues, tickets, bookings
- **Plural nouns**: `/events`, never `/event`
- Nest only for required parent-child: `/events/{id}/tickets`
- Optional filters go in query params: `/tickets?event_id=123&section=VIP`
- Path params identify (`/events/123`), query params filter/sort/paginate, body carries complex payloads

## Idempotency by Method

| Method | Idempotent | Notes |
|---|---|---|
| GET | ✅ | Safe and idempotent |
| PUT | ✅ | Replaces whole resource |
| DELETE | ✅ | |
| **POST** | ❌ | Retries create duplicates |
| **PATCH** | ⚠️ | Depends on implementation |

## The Nine Principles

1. **Resources over actions** — `/users/{id}/bookings`, not `/getUserBookings`
2. **Consistency everywhere** — same naming, param style, response shape across all endpoints
3. **Least surprise** — follow HTTP conventions
4. **Statelessness** — every request carries its own auth, params, payload
5. **Safe retries** — design for idempotency
6. **Pagination by default** — every collection endpoint, with a size cap
7. **Security by default** — authenticate everything; authorization opt-in per resource
8. **Evolve without breaking** — adding fields is safe; removing/renaming needs a version
9. **Actionable errors** — right status class + machine-readable code + human message

## Pagination

- **Offset** (`?offset=20&limit=10`) — intuitive, breaks when data shifts under you
- **Cursor** (`?cursor=…&limit=10`) — stable for realtime feeds; return `next_cursor` in the response

## Idempotency Keys

For risky POSTs (bookings, payments), the client sends a unique key in a header. The server stores the key alongside the first result; a retry with the same key returns the stored response instead of rebooking. This is the standard answer for "what if the network drops after we charge them?"

## Versioning

- **URL** (`/v1/events`) — most explicit, easiest to test, best for interviews
- **Header** (`Accept-Version: v2`) — cleaner URLs, less discoverable

## Security

**Authentication** = who are you. **Authorization** = are you allowed.

- **API keys** — server-to-server, third-party developers; long random strings stored server-side
- **JWT** — user sessions; encodes identity + expiry, signed, statelessly validated by any service
- **RBAC** — roles carry permissions; check both a valid token *and* resource ownership/admin
- **Rate limiting** — per-user / per-IP / per-endpoint, return **429**, enforce at the API gateway

**The hard rule:** never trust the request body. Derive the current user from the auth token, never from a client-supplied user ID.

## Interview Approach

"Most interviewers don't care about your API design being perfect. They want to see that you can design a reasonable API and move on to the more complex parts of your system."

- ~5 minutes maximum
- Default to REST
- Show one or two endpoints that demonstrate resource modeling
- Name your authn/authz strategy and rate limiting, then move on
- Justify with a principle: "idempotency key on bookings so retries are safe"
