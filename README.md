# 🔐 DSAR Portal — Data Subject Access Request MVP

> A production-grade MVP for automating GDPR/CCPA Data Subject Access Requests.  
> Built with **Spring Boot 3.2 + Java 17** (backend) and **Vue 3 + Vite + Pinia** (frontend).

[![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Vue.js](https://img.shields.io/badge/Vue-3.x-4FC08D?logo=vuedotjs&logoColor=white)](https://vuejs.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📖 What is a DSAR?

A **Data Subject Access Request (DSAR)** is a formal request by an individual to an organization to access, delete, or correct personal data held about them — a right guaranteed under **GDPR** (Articles 15–17), **CCPA**, and similar privacy regulations. Organizations must respond within **30 days**.

This portal automates the full lifecycle:
```
Customer submits request → Admin reviews → Status updated → Audit trail logged
```

---

## ✨ Features

| Feature | Details |
|---|---|
| 🔐 Role-Based Auth | HTTP Basic Auth — `CUSTOMER` and `ADMIN` roles, BCrypt passwords |
| 📋 Request Lifecycle | `SUBMITTED → IN_REVIEW → COMPLETED / REJECTED` |
| 📅 SLA Enforcement | Auto 30-day due date per GDPR; overdue requests highlighted in red |
| 🔒 Audit Trail | Immutable log of every action with `previousValue → newValue` |
| 📊 Admin Dashboard | Stats by status/type, filters, full request management |
| 🗄️ In-Memory Store | `ConcurrentHashMap` — zero DB setup, runs instantly |
| ✅ Test Suite | 26 unit + integration tests (service, repository, controller) |
| 🌙 Dark Mode | Full light/dark theme support in the Vue frontend |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                  Vue 3 Frontend  :5173                    │
│  Login → CustomerDashboard / AdminDashboard / AuditLog   │
│  Pinia stores (auth, requests) + Vue Router guards       │
└────────────────────────┬───────────────────────────────┘
                         │ HTTP + Basic Auth
                         ▼
┌─────────────────────────────────────────────────────────┐
│           Spring Boot 3.2  :8080                         │
│  SecurityConfig (STATELESS Basic Auth + CORS)            │
│  ┌──────────────┐  ┌─────────────────┐  ┌───────────┐  │
│  │ AuthController │  │ DsarReqController │  │ AuditCtrl │  │
│  └──────┬───────┘  └────────┬────────┘  └─────┬─────┘  │
│           └──────────────┴────────────────────┴───────────┘  │
│                          │                               │
│             DsarRequestService + AuditService             │
│                          │                               │
│    ┌────────────────┴────────────────────┐              │
│    │  ConcurrentHashMap In-Memory Stores                 │              │
│    │  UserRepository | DsarRequestRepository | AuditRepo │              │
│    └─────────────────────────────────────────┘              │
└─────────────────────────────────────────────────────────┘
```

---

## 🚀 Quick Start

### Prerequisites

| Tool | Version | Check |
|---|---|---|
| Java | 17+ | `java -version` |
| Maven | 3.9+ | `mvn -version` |
| Node.js | 18+ | `node -version` |
| npm | 9+ | `npm -version` |

### 1. Clone the Repository

```bash
git clone https://github.com/sandip-poudel/dsar-portal.git
cd dsar-portal
```

### 2. Start the Backend

```bash
cd backend
mvn spring-boot:run
```

✅ Backend starts at **http://localhost:8080**  
You will see: `✅ DSAR Portal started — demo users seeded`

### 3. Start the Frontend

Open a **new terminal**:

```bash
cd frontend
npm install
npm run dev
```

✅ Frontend opens at **http://localhost:5173**

---

## 🔑 Demo Credentials

| Role | Username | Password | Access Level |
|---|---|---|---|
| **Admin** | `admin` | `admin123` | Full dashboard, all requests, audit trail, stats |
| **Customer** | `alice` | `alice123` | Submit & track own DSAR requests |
| **Customer** | `bob` | `bob123` | Submit & track own DSAR requests |
| **Customer** | `charlie` | `charlie123` | Submit & track own DSAR requests |

---

## 🧪 Running Tests

```bash
cd backend

# Run all 26 tests
mvn test

# Run with verbose output
mvn test -Dsurefire.useFile=false

# Run a specific test class
mvn test -Dtest=DsarRequestServiceTest
mvn test -Dtest=DsarRequestControllerTest
mvn test -Dtest=AuditServiceTest
mvn test -Dtest=DsarRequestRepositoryTest
```

### Test Coverage

| Test Class | Tests | What Is Covered |
|---|---|---|
| `DsarPortalApplicationTests` | 1 | Spring context loads cleanly |
| `DsarRequestRepositoryTest` | 6 | CRUD, filter by user/status/type, count operations |
| `AuditServiceTest` | 3 | Log creation, filtering by requestId, counting |
| `DsarRequestServiceTest` | 8 | Submit, list, RBAC ownership, update status, stats |
| `DsarRequestControllerTest` | 8 | HTTP layer: auth, role enforcement, validation, status codes |
| **Total** | **26** | **All layers covered** |

---

## 📡 API Reference

All endpoints use **HTTP Basic Auth**: `Authorization: Basic base64(username:password)`

| Method | Endpoint | Role | Description |
|---|---|---|---|
| `GET` | `/api/auth/me` | Any | Current user profile + role |
| `POST` | `/api/requests` | `CUSTOMER` | Submit a new DSAR |
| `GET` | `/api/requests/my` | `CUSTOMER` | My own requests |
| `GET` | `/api/requests` | `ADMIN` | All requests (`?status=&type=`) |
| `GET` | `/api/requests/{id}` | Any | Request detail (customers: own only) |
| `PUT` | `/api/requests/{id}/status` | `ADMIN` | Update status + add notes |
| `GET` | `/api/requests/stats` | `ADMIN` | Dashboard statistics |
| `GET` | `/api/audit-logs` | `ADMIN` | Full audit trail |
| `GET` | `/api/audit-logs/request/{id}` | `ADMIN` | Audit trail for one request |

### cURL Examples

```bash
# Check your identity
curl -u alice:alice123 http://localhost:8080/api/auth/me

# Submit a DSAR (as customer)
curl -u alice:alice123 -X POST http://localhost:8080/api/requests \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Alice Johnson","email":"alice@example.com","type":"ACCESS","description":"Please send me all personal data you hold about me per GDPR Article 15."}'

# Admin: view all requests
curl -u admin:admin123 http://localhost:8080/api/requests

# Admin: filter by status
curl -u admin:admin123 "http://localhost:8080/api/requests?status=SUBMITTED"

# Admin: update request status (replace {id} with actual UUID)
curl -u admin:admin123 -X PUT "http://localhost:8080/api/requests/{id}/status" \
  -H "Content-Type: application/json" \
  -d '{"status":"COMPLETED","adminNotes":"Data package securely delivered via encrypted email."}'

# Admin: full audit trail
curl -u admin:admin123 http://localhost:8080/api/audit-logs

# Admin: stats
curl -u admin:admin123 http://localhost:8080/api/requests/stats
```

---

## 📁 Project Structure

```
dsar-portal/
├── README.md
├── LICENSE
├── .gitignore
├── docs/
│   ├── class-diagram.md          ← UML Class Diagram (Mermaid)
│   ├── sequence-submit.md         ← Sequence: Customer submits DSAR
│   └── sequence-admin-update.md   ← Sequence: Admin updates status
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/dsar/portal/
│       │   ├── DsarPortalApplication.java
│       │   ├── config/SecurityConfig.java
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   ├── DsarRequestController.java
│       │   │   └── AuditLogController.java
│       │   ├── dto/
│       │   │   ├── SubmitRequestDto.java
│       │   │   ├── UpdateStatusDto.java
│       │   │   ├── DsarRequestResponse.java
│       │   │   ├── UserResponse.java
│       │   │   ├── StatsResponse.java
│       │   │   └── AuditLogResponse.java
│       │   ├── exception/
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   ├── ResourceNotFoundException.java
│       │   │   └── AccessDeniedException.java
│       │   ├── model/
│       │   │   ├── DsarRequest.java
│       │   │   ├── AuditLog.java
│       │   │   ├── User.java
│       │   │   ├── Role.java
│       │   │   ├── RequestType.java
│       │   │   └── RequestStatus.java
│       │   ├── repository/
│       │   │   ├── UserRepository.java
│       │   │   ├── DsarRequestRepository.java
│       │   │   └── AuditLogRepository.java
│       │   ├── security/
│       │   │   ├── DsarUserDetails.java
│       │   │   └── DsarUserDetailsService.java
│       │   └── service/
│       │       ├── DsarRequestService.java
│       │       ├── AuditService.java
│       │       └── DataSeedService.java
│       └── test/java/com/dsar/portal/
│           ├── DsarPortalApplicationTests.java
│           ├── controller/DsarRequestControllerTest.java
│           ├── repository/DsarRequestRepositoryTest.java
│           └── service/
│               ├── DsarRequestServiceTest.java
│               └── AuditServiceTest.java
└── frontend/
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── main.js
        ├── App.vue
        ├── api.js
        ├── style.css
        ├── router/index.js
        ├── stores/
        │   ├── auth.js
        │   └── requests.js
        ├── views/
        │   ├── LoginView.vue
        │   ├── CustomerDashboard.vue
        │   ├── AdminDashboard.vue
        │   └── AuditLogView.vue
        └── components/
            ├── AppHeader.vue
            ├── RequestForm.vue
            ├── RequestTable.vue
            ├── RequestDetail.vue
            ├── StatusUpdateForm.vue
            ├── StatusBadge.vue
            ├── TypeBadge.vue
            ├── RoleBadge.vue
            └── StatCard.vue
```

---

## 🔒 Security Design

- **Stateless sessions** — `SessionCreationPolicy.STATELESS`, zero server-side session state
- **BCrypt passwords** — `BCryptPasswordEncoder` strength 10
- **Role enforcement at two layers** — Spring Security HTTP rules + service-layer ownership checks
- **CORS** — Whitelisted for `localhost:5173` and `localhost:3000` only
- **Input validation** — `@Valid` Bean Validation on all DTOs with descriptive error responses
- **Admin cannot submit** — `POST /api/requests` is restricted to `ROLE_CUSTOMER` only

---

## 🗺️ UML Diagrams

All diagrams are in [`docs/`](docs/) and render automatically on GitHub:

- [📍 Class Diagram](docs/class-diagram.md)
- [🔄 Sequence: Submit DSAR](docs/sequence-submit.md)
- [🔄 Sequence: Admin Update Status](docs/sequence-admin-update.md)

---

## 📦 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.2.3, Java 17 |
| Security | Spring Security 6, HTTP Basic Auth, BCrypt |
| Validation | Jakarta Bean Validation (`spring-boot-starter-validation`) |
| In-Memory Store | `ConcurrentHashMap` (thread-safe, no DB) |
| Testing | JUnit 5, AssertJ, MockMvc, Spring Security Test |
| Frontend | Vue 3, Vite 5, Pinia, Vue Router 4 |
| HTTP Client | Axios |
| Utilities | Lombok |

---

## 📄 License

MIT — see [LICENSE](LICENSE)
