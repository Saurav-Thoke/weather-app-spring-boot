FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/WeatherApp2-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","\WeatherApp2-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080