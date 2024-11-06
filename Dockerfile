# Étape de build
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
WORKDIR /home/immo/authentification
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape de run
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=build /home/immo/authentification/target/*.jar /app/authentification.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","authentification.jar"]
