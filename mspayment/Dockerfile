FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn clean package -DskipTests
RUN ls -l /app/target

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/mspayment-0.0.1-SNAPSHOT.jar /app/ms-payment.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/ms-payment.jar"]
