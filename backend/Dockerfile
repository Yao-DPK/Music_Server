#Etape 1 : Build l'application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
RUN ls -la /app

#Etape2: Lancer l'application

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN ls -la /app

EXPOSE 8087
# Attends que la base soit prête avant de démarrer
ENTRYPOINT [ "java", "-jar",  "./app.jar"]
