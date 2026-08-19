# ─── Build Stage ────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN for i in 1 2 3 4 5; do mvn clean package -DskipTests -q && exit 0 || sleep 5; done; exit 1

# ─── Runtime Stage ───────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/problem-service-*.jar app.jar
EXPOSE 8086
HEALTHCHECK --interval=15s --timeout=5s --start-period=60s --retries=5 \
  CMD wget -qO- http://localhost:8086/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
