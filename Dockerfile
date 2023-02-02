# Use an official Maven image as the builder image
FROM maven:3.8.3-openjdk-17 as builder

# Set the working directory in the container to /app
WORKDIR /app

# Copy the application's pom.xml file
COPY /news-service/pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY /news-service/src /app/src

# Build the application
RUN mvn package

# Use an official OpenJDK image as the runtime image
FROM openjdk:17-jdk-slim-buster

# Set the working directory in the container to /app
WORKDIR /app

# Copy the compiled JAR file from the builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Start the application using the "java -jar" command
CMD ["java", "-jar", "app.jar"]