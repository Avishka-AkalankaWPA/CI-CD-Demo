# Stage 1: Build stage with Maven & OpenJDK 17
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# Copy dependency definition first for caching
COPY pom.xml .
COPY config/ config/
RUN mvn dependency:go-offline -B

# Copy source code and build package (skipping tests since CI runs tests separately)
COPY src/ src/
RUN mvn clean package -DskipTests -B

# Stage 2: Minimal runtime environment
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy compiled jar artifact from builder stage
COPY --from=builder /app/target/*.jar app.jar
RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080
ENV PORT=8080
ENV APP_ENV=Production

HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/ || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
