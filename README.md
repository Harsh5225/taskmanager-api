# TaskManager API

A highly scalable, production-ready RESTful backend API designed for enterprise task management. Built with **Java 21** and **Spring Boot 3.2.5**, this service demonstrates advanced backend engineering principles including dynamic database querying, secure JWT authentication pipelines with refresh token rotation, strict rate limiting, multi-stage containerization, and live API interactive documentation.

> 🌐 **Live Interactive API Documentation**: [Explore Swagger UI on Render](https://taskmanager-api-jxua.onrender.com/swagger-ui/index.html#/)

---

## ✨ Key Technical Features

* **Live Deployment & Multi-Stage Docker Build**: Containerized using an optimized multi-stage `Dockerfile` to create ultra-lightweight binaries, currently deployed live on **Render**.
* **Advanced Authentication & Authorization**: Fully integrated Spring Security featuring JWT (JSON Web Tokens) with token persistence and refresh token rotation. Includes strict Role-Based Access Control (RBAC) supporting `ADMIN` and `USER` authorities with custom 403 Forbidden interceptors.
* **Intelligent API Rate Limiting**: IP-based rate limiting integrated on sensitive endpoints using **Bucket4j**, mitigating brute-force attacks by returning structured `429 Too Many Requests` responses.
* **Dynamic Search & Pagination**: Flexible data retrieval using custom JPQL dynamic queries to support multi-field filtering (such as status and keywords simultaneously) alongside pagination and sorting.
* **Interactive API Documentation**: Embedded **Springdoc OpenAPI (`2.5.0`)** providing live interactive Swagger UI endpoints for real-time API exploration and testing.
* **Centralized Exception Handling**: Global exception interception (`@ControllerAdvice`) ensuring standardized, predictable JSON error payloads across all endpoints.

---

## 🛠️ Tech Stack & Dependencies

* **Language**: Java 21
* **Framework**: Spring Boot 3.2.5 (`spring-boot-starter-web`, `data-jpa`, `security`, `validation`)
* **Database**: PostgreSQL
* **Security & Tokens**: Spring Security, JJWT (`0.11.5`)
* **Rate Limiting**: Bucket4j (`8.15.0`)
* **API Documentation**: Springdoc OpenAPI UI (`2.5.0`)
* **Utilities**: Lombok (`1.18.34`)
* **Build & DevOps**: Maven (`3.11.0` Compiler Plugin), Docker, Docker Compose, Render Deployment

---

## 🌐 Live API Access & Swagger UI

You can interactively test all live endpoints directly in your browser:

* **Interactive Swagger UI**: [https://taskmanager-api-jxua.onrender.com/swagger-ui/index.html#/](https://taskmanager-api-jxua.onrender.com/swagger-ui/index.html#/)
* **OpenAPI Specification Docs**: `[https://taskmanager-api-jxua.onrender.com/v3/api-docs](https://taskmanager-api-jxua.onrender.com/v3/api-docs)`

---

## 📂 Project Architecture

```text
taskmanager-api/
├── src/main/java/com/taskmanager/taskmanager_api/
│   ├── config/        # Application configurations (Security, CORS, OpenAPI, Rate Limiting)
│   ├── controller/    # REST API endpoints & input routing
│   ├── dto/           # Data Transfer Objects (Requests & Responses)
│   ├── exception/     # Global exception handlers (@ControllerAdvice)
│   ├── model/         # JPA Entities mapping to PostgreSQL tables
│   ├── repository/    # Spring Data JPA repositories & dynamic JPQL queries
│   ├── security/      # JWT filters, authentication providers, UserDetailsService
│   └── service/       # Core business logic and transaction management
├── Dockerfile         # Multi-stage build specification for production builds
├── docker-compose.yml # Container orchestration for local multi-container development
└── pom.xml            # Dependency and build management

```

---

## 🚀 Local Development Setup

### Prerequisites

* **Java 21 JDK**
* **Maven 3.8+** (or use the included `./mvnw` wrapper)
* **PostgreSQL** or **Docker**

### 1. Repository Setup

```bash
git clone https://github.com/Harsh5225/taskmanager-api.git
cd taskmanager-api

```

### 2. Environment Variables & Properties

Configure your database connection and secret key in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=postgres
spring.datasource.password=your_secure_password
spring.jpa.hibernate.ddl-auto=update

# JWT Configuration
jwt.secret=YOUR_SUPER_SECRET_BASE64_ENCODED_KEY_HERE
jwt.expiration=86400000

```

### 3. Build & Run

**Natively:**

```bash
./mvnw clean install
./mvnw spring-boot:run

```

**Using Docker Compose (Local DB + API):**

```bash
docker-compose up --build

```

---

## 🛡️ Key Security Features & Rate Limiting

### 1. Bucket4j Rate Limiting

To defend against brute-force attacks on sensitive paths (e.g., login), the API checks incoming IP buckets. Exceeding token capacity yields a structured JSON error response:

```json
{
  "status": 429,
  "error": "Too Many Requests",
  "message": "API rate limit exceeded. Please try again later."
}

```

### 2. Refresh Token Rotation

Access tokens carry short validity periods. Upon requesting a new access token via the `/api/auth/refresh` endpoint, the API invalidates the previous refresh token and issues a new pair, mitigating token replay vulnerability risks.
