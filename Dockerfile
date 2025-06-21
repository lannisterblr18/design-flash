# Stage 1: Build the application using Maven
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the packaged JAR with a lightweight JDK
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Optional: Make the port configurable (Render/Heroku uses $PORT)
ENV PORT 8080
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
