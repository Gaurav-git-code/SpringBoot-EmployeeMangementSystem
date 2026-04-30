# Employee Management System

A production-grade REST API built with Spring Boot 3.x, featuring JWT authentication, role-based access control, PostgreSQL persistence, Docker containerisation, and a full CI/CD pipeline via GitHub Actions.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5 |
| Security | Spring Security + JWT (jjwt 0.12.6) |
| Persistence | Spring Data JPA + PostgreSQL 15 |
| Validation | Jakarta Bean Validation |
| Build Tool | Maven |
| Containerisation | Docker + Docker Compose |
| CI/CD | GitHub Actions |
| Code Generation | Lombok |

---

## Architecture

```
┌─────────────────────────────────────────────┐
│                   Client                    │
└──────────────────────┬──────────────────────┘
                       │ HTTP Request + JWT
┌──────────────────────▼──────────────────────┐
│         JwtAuthenticationFilter             │
│         (validates token on every request)  │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│              REST Controllers               │
│   AuthController  EmployeeController        │
│   DepartmentController                      │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│           Service Layer (Interface/Impl)    │
│   EmployeeService   DepartmentService       │
│   EmsUserService                            │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│              Repository Layer               │
│         Spring Data JPA Repositories        │
└──────────────────────┬──────────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│              PostgreSQL Database            │
└─────────────────────────────────────────────┘
```

---

## Features

- JWT-based stateless authentication
- Role-based access control — `ADMIN` and `USER` roles via `@PreAuthorize`
- Full CRUD for Employees and Departments
- Paginated and sortable list endpoints
- Many-to-One relationship — Employee belongs to Department
- Global exception handling via `@ControllerAdvice`
- Bean validation on all request DTOs
- Multi-stage Docker build for minimal image size
- Docker Compose for local development
- CI/CD pipeline — tests on every push, Docker image pushed to registry on merge to main

---

## Prerequisites

- Java 17+
- Maven 3.9+
- Docker and Docker Compose
- PostgreSQL 15 (if running without Docker)

---

## Running Locally

### Option 1 — Docker Compose (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/Gaurav-git-code/SpringBoot-EmployeeMangementSystem.git
cd SpringBoot-EmployeeMangementSystem
```

2. Create a `.env` file in the project root:
```env
DB_URL=jdbc:postgresql://db:5432/ems
DB_USER=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret_minimum_32_characters
```

3. Start the application:
```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`

---

### Option 2 — Run Locally with External PostgreSQL

1. Create a PostgreSQL database named `ems`

2. Set the following environment variables:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/ems
export DB_USER=your_db_user
export DB_PASSWORD=your_db_password
export JWT_SECRET=your_jwt_secret_minimum_32_characters
```

3. Run the application:
```bash
mvn spring-boot:run
```

---

## API Reference

### Authentication

All protected endpoints require a Bearer token in the `Authorization` header:
```
Authorization: Bearer <your_jwt_token>
```

#### Register
```
POST /api/v1/auth/signup
```
Request body:
```json
{
  "username": "john",
  "password": "password123"
}
```

#### Login
```
POST /api/v1/auth/login
```
Request body:
```json
{
  "username": "john",
  "password": "password123"
}
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Department Endpoints

| Method | Endpoint | Role | Description |
|---|---|---|---|
| `POST` | `/api/v1/department` | ADMIN | Create a department |
| `GET` | `/api/v1/department` | USER | Get all departments (paginated) |
| `GET` | `/api/v1/department/{id}` | USER | Get department by ID |
| `GET` | `/api/v1/department/{id}/allEmployees` | USER | Get all employees in a department |
| `PUT` | `/api/v1/department/{id}` | ADMIN | Update a department |
| `DELETE` | `/api/v1/department/{id}` | ADMIN | Delete a department |

#### Create Department — Request Body
```json
{
  "departmentName": "Engineering",
  "departmentType": "Technical"
}
```

#### Get All Departments — Query Parameters
| Parameter | Default | Description |
|---|---|---|
| `pageNo` | 0 | Page number |
| `pageSize` | 2 | Results per page |
| `sortBy` | departmentName | Field to sort by |
| `sortDir` | ASC | Sort direction (ASC/DESC) |

---

### Employee Endpoints

| Method | Endpoint | Role | Description |
|---|---|---|---|
| `POST` | `/api/v1/employee` | ADMIN | Create an employee |
| `GET` | `/api/v1/employee` | USER | Get all employees (paginated) |
| `GET` | `/api/v1/employee/{id}` | USER | Get employee by ID |
| `PUT` | `/api/v1/employee/{id}` | ADMIN | Update an employee |
| `DELETE` | `/api/v1/employee/{id}` | ADMIN | Delete an employee |

#### Create Employee — Request Body
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "departmentId": 1
}
```

#### Get All Employees — Query Parameters
| Parameter | Default | Description |
|---|---|---|
| `departmentId` | required | Filter by department |
| `pageNo` | 0 | Page number |
| `pageSize` | 2 | Results per page |
| `sortBy` | firstName | Field to sort by |
| `sortDir` | ASC | Sort direction (ASC/DESC) |

---

## Running Tests

```bash
mvn test
```

The test suite covers three layers:

- **Controller layer** — `@WebMvcTest` with Mockito for HTTP request/response testing
- **Service layer** — `@ExtendWith(MockitoExtension.class)` for unit testing business logic
- **Repository layer** — `@DataJpaTest` with H2 in-memory database for JPA query testing

---

## CI/CD Pipeline

The GitHub Actions pipeline runs on every push and pull request:

```
push / pull_request to main or dev
           ↓
    builds job
    ├── Checkout code
    ├── Setup Java 17 (Temurin)
    ├── Restore Maven dependency cache
    ├── Start PostgreSQL 15 service container
    └── Run mvn verify (all tests)
           ↓
    deploy job (main branch only, runs if builds passes)
    ├── Checkout code
    ├── Login to Docker Hub
    ├── Build Docker image (tagged :latest and :<commit-sha>)
    └── Push image to Docker Hub
```

Branch protection on `main` requires all checks to pass before merging.

---

## Docker Image

The application uses a multi-stage Docker build:

- **Stage 1 (builder)** — Maven + JDK 17 image compiles the code and produces the JAR
- **Stage 2 (runtime)** — Lightweight JRE 17 image runs only the JAR

This keeps the final image small — no Maven, no source code, no build tools in production.

---

## Project Structure

```
src/
├── main/java/com/darknightcoder/ems/
│   ├── config/          # Security configuration
│   ├── controller/      # REST controllers
│   ├── entity/          # JPA entities
│   ├── enums/           # Role enums
│   ├── exception/       # Global exception handling
│   ├── mapper/          # Entity ↔ DTO mappers
│   ├── model/           # DTOs and response models
│   ├── repository/      # Spring Data JPA repositories
│   ├── security/        # JWT filter, utils, user details
│   └── service/         # Service interfaces and implementations
└── test/java/com/darknightcoder/ems/
    ├── controller/      # WebMvcTest controller tests
    ├── repository/      # DataJpaTest repository tests
    └── service/         # Mockito service unit tests
```

---

## Author

**Gaurav Suman**
[GitHub](https://github.com/Gaurav-git-code)
