FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/user-api.jar

EXPOSE 8083

CMD ["java","-jar","user-api.jar"]