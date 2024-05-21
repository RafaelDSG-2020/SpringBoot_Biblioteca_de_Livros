FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/book-api.jar

EXPOSE 8086

CMD ["java","-jar","book-api.jar"]