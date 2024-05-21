FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/server-api.jar

EXPOSE 8081

CMD ["java","-jar","server-api.jar"]