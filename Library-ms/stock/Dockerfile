FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/stock-api.jar

EXPOSE 8084

CMD ["java","-jar","stock-api.jar"]