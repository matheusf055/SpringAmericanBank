FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/eurekaserver-0.0.1-SNAPSHOT.jar /app/eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/eureka-server.jar"]
