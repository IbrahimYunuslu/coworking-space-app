FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradlew .
COPY gradle gradle

COPY build.gradle .

COPY src src

RUN ./gradlew build --no-daemon

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/coworking-space-app-0.0.1-SNAPSHOT.jar"]