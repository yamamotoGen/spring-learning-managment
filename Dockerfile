#Stage 1
FROM maven:3.8.8-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

#Stage 2
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/spring-learning-management-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8085

CMD ["java", "-jar", "app.jar"]