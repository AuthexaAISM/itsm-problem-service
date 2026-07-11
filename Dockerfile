# ─── Build Stage ────────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests -q

# ─── Runtime Stage ───────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/problem-service-*.jar app.jar
EXPOSE 8086
HEALTHCHECK --interval=15s --timeout=5s --start-period=60s --retries=5 \
  CMD wget -qO- http://localhost:8086/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
