# Step 1: Use official Java 21 runtime
FROM eclipse-temurin:21-jre

# Step 2: Working directory
WORKDIR /app

# Step 3: Copy JAR
COPY target/taskmanager-api-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose port
EXPOSE 8080

# Step 5: Run Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
