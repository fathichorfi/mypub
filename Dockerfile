FROM openjdk:17-jdk-slim as build
ARG JAR_FILE=target/*.jar
COPY wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it
WORKDIR /app
COPY ${JAR_FILE} my-app.jar
EXPOSE 8899
ENTRYPOINT ["java", "-jar", "my-app.jar"]