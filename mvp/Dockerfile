#Etape 1 : Build l'application
FROM maven:3.8.8-eclipse-temurin-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#Etape2: Lancer l'application

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
