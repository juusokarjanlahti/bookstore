# Build stage
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the build container
WORKDIR /app

# Copy necessary files for Maven build
COPY pom.xml . 
COPY mvnw . 
COPY .mvn .mvn

# Download dependencies (this is cached if pom.xml hasn't changed)
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src src

# Package the application (skip tests to speed up build)
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

# Set the working directory in the runtime container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
