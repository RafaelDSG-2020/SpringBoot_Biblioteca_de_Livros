FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/gateway-api.jar

EXPOSE 8082

CMD ["java","-jar","gateway-api.jar"]