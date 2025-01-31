# Use the desired JDK version
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /

# Copy the application JAR file
COPY build/libs/ms-booking-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]