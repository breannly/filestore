FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/filestore-1.0.0.jar .

CMD ["java", "-jar", "filestore-1.0.0.jar"]