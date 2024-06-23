# Build stage
FROM maven:3.9.7-amazoncorretto-21 AS build
WORKDIR /app
COPY ../../ /app
RUN mvn -B clean package

# Runtime stage
FROM maven:3.9.7-amazoncorretto-21 AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]