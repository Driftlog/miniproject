FROM maven:3-eclipse-temurin-17 as mvnbuilder

WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY src src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=mvnbuilder /app/target/*.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar /app/app.jar