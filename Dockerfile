#Maven Build
FROM maven:3.8.3-openjdk-17-slim AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

#Run
FROM openjdk:17
COPY --from=builder /app/target/A2-17647-customer-0.0.1-SNAPSHOT.jar a1-app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "a1-app.jar"]