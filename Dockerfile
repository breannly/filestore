FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY build.gradle settings.gradle gradlew gradle.properties /app/
COPY gradle /app/gradle
COPY src /app/src

RUN ./gradlew build

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/filestore-1.0.0.jar .

CMD ["java", "-jar", "filestore-1.0.0.jar"]