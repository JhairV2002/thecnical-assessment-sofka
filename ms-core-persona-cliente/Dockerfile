FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
RUN mkdir ".m2"
RUN echo "<settings><servers><server><id>nexus</id><username>${NEXUS_USERNAME}</username><password>${NEXUS_PASSWORD}</password></server></servers></settings>" > .m2/settings.xml
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/ms-core-persona-cliente-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]