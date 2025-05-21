# Build stage
FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /workspace/app

# Copy only the files needed for dependencies
COPY pom.xml .
# Download dependencies first to leverage Docker cache
RUN mvn dependency:go-offline

# Copy source files
COPY src src

# Build the application
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built application from builder
COPY --from=builder /workspace/app/target/*.jar app.jar

# Create log directory
RUN mkdir -p /log/finTech

# Expose the application port
EXPOSE 8282

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]