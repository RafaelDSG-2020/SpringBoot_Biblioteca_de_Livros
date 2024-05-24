FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/loanofbooks-api.jar

EXPOSE 8087

CMD ["java","-jar","loanofbooks-api.jar"]