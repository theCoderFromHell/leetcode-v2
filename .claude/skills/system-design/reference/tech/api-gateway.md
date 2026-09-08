# API Gateway

Single entry point for client requests, routing them to backend services — a hotel front desk instead of guests wandering into individual rooms.

## Request Flow

1. Validate the request (format, headers, body)
2. Apply middleware — authentication, rate limiting, SSL termination
3. **Route to the backend service**
4. Handle backend communication, possibly translating protocol
5. Transform the response to the client's format
6. Cache where applicable

> **"The gateway's primary function is request routing"** — middleware is secondary. Say routing first.

## Placement

Sits between clients and microservices. A dedicated load balancer usually fronts the gateway itself; the gateway may then load-balance across backend instances. **Typically stateless**, so it scales horizontally.

## Gateway vs Load Balancer

They are not the same box. A load balancer distributes traffic across identical instances (L4/L7). A gateway makes *application-level* decisions — which service, is this caller authenticated, are they over quota, does the response need reshaping.

## When to Use It

✅ Microservices with multiple backend services
❌ A monolith or single-service system — it's pure overhead

## Options

**Managed:** AWS API Gateway, Azure API Management, Google Cloud Endpoints
**Open source:** Kong (NGINX-based), Tyk, Express Gateway

## Interview Advice

Say *"add an API Gateway to handle routing and basic middleware,"* draw the box, and move on. This is not the interesting part of any design, and dwelling here spends time you need for deep dives.

> Paywalled on the source page: knowledge check, some problem breakdowns.
