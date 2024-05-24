FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y net-tools

WORKDIR /app

COPY target/*.jar /app/author-api.jar

EXPOSE 8085

CMD ["java","-jar","author-api.jar"]