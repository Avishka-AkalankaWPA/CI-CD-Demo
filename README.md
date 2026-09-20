# 🚀 Java Spring Boot CI/CD Showcase App

A clean, production-ready **Spring Boot 3 (Java 17)** application built to demonstrate **Continuous Integration & Continuous Deployment (CI/CD)** using **GitHub Actions**, Checkstyle linting, JUnit 5 / Mockito automated testing, JaCoCo code coverage, Docker containerization, and **Railway Cloud Deployment**.

---

## 🌟 Features

- **Public REST API Integration**: Calls the free Spaceflight News API (`api.spaceflightnewsapi.net`) using Spring 3 `RestClient` with configurable timeouts and graceful fallbacks.
- **Thymeleaf UI**: Glassmorphic responsive news dashboard showing live articles, source publisher, build version, and pipeline health indicator.
- **Clean Architecture & Code Standards**:
  - Immutability with Java 17 `record` DTOs (`NewsArticleDto`, `NewsApiResponse`).
  - Decoupled interface service pattern (`NewsService` interface + `SpaceflightNewsServiceImpl`).
  - Constructor injection for all Spring components.
  - Fail-safe exception handling preventing UI crashes when offline.
- **Automated Quality Controls**:
  - **Linting**: Enforced with `maven-checkstyle-plugin`.
  - **Testing**: JUnit 5 + Mockito unit tests & Spring `@WebMvcTest` integration tests.
  - **Code Coverage**: JaCoCo maven plugin set to fail build if line coverage drops below threshold.
- **Multi-Stage Containerization**: Minimal Alpine JRE Docker container image running under non-root user.

---

## 📁 Project Structure

```
CI-CD/
├── .github/
│   └── workflows/
│       └── ci-cd.yml           # GitHub Actions CI/CD Pipeline
├── config/
│   └── checkstyle/
│       └── checkstyle.xml      # Checkstyle Code Quality Rules
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/newsapp/
│   │   │       ├── NewsAppApplication.java
│   │   │       ├── config/
│   │   │       │   └── RestClientConfig.java
│   │   │       ├── controller/
│   │   │       │   └── NewsController.java
│   │   │       ├── dto/
│   │   │       │   ├── NewsApiResponse.java
│   │   │       │   └── NewsArticleDto.java
│   │   │       └── service/
│   │   │           ├── NewsService.java
│   │   │           └── impl/
│   │   │               └── SpaceflightNewsServiceImpl.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── templates/
│   │           └── index.html  # Thymeleaf Web UI
│   └── test/
│       └── java/
│           └── com/example/newsapp/
│               ├── controller/
│               │   └── NewsControllerTest.java
│               └── service/
│                   └── SpaceflightNewsServiceTest.java
├── Dockerfile                  # Multi-Stage Dockerfile
├── pom.xml                     # Maven Build & Plugin Configurations
└── README.md
```

---

## 🔄 CI/CD Pipeline Workflow

The GitHub Actions workflow (`.github/workflows/ci-cd.yml`) automates the software delivery pipeline:

```
[ Developer Push / Pull Request ]
              │
              ▼
    1. Checkout Code & JDK 17
              │
              ▼
    2. Checkstyle Lint Audit ──(Fails if code style broken)
              │
              ▼
    3. JUnit 5 & Mockito Tests ──(Fails if tests break)
              │
              ▼
    4. JaCoCo Coverage Audit ──(Fails if coverage < 70%)
              │
              ▼
    5. Package Executable JAR & Docker Image
              │
              ▼ (Only on push to main branch)
    6. Deploy to Railway Cloud
```

---

## 💻 Local Execution & Commands

### 1. Run Application Locally
```bash
mvn spring-boot:run
```
Open your browser at `http://localhost:8080`.

### 2. Run Code Linting (Checkstyle)
```bash
mvn checkstyle:check
```

### 3. Run Test Suite & Generate Coverage Report
```bash
mvn clean test jacoco:report
```
View coverage report in browser: `target/site/jacoco/index.html`.

### 4. Build & Package Executable Jar
```bash
mvn clean package
```

### 5. Build Docker Image
```bash
docker build -t news-cicd-demo:latest .
docker run -p 8080:8080 news-cicd-demo:latest
```

---

## ☁️ Deployment to Railway (Paid Plan)

1. Push your repository to GitHub.
2. Log into **Railway** (`railway.app`) and copy your **Railway Account API Token** or **Project Token**.
3. In your GitHub Repository, navigate to **Settings** $\rightarrow$ **Secrets and variables** $\rightarrow$ **Actions**.
4. Add a New Repository Secret:
   - **Name**: `RAILWAY_TOKEN`
   - **Value**: `<your-railway-token>`
5. Any push to the `main` branch will automatically trigger GitHub Actions to run the full CI suite and deploy the app directly to Railway!
