FROM node:18 AS angbuilder

WORKDIR /app

COPY miniproject-frontend/src src
COPY miniproject-frontend/angular.json .
COPY miniproject-frontend/package.json .
COPY miniproject-frontend/package-lock.json .
COPY miniproject-frontend/tsconfig.app.json .
COPY miniproject-frontend/tsconfig.json .

RUN npm i -g @angular/cli
RUN npm ci
RUN ng build
  
FROM maven:3-eclipse-temurin-17 AS mvnbuilder

WORKDIR /app

COPY miniproject-backend/mvnw .
COPY miniproject-backend/pom.xml .
COPY miniproject-backend/src src

COPY --from=angbuilder /app/dist/* /app/src/main/resources/static

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=mvnbuilder /app/target/*.jar app.jar

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar /app/app.jar