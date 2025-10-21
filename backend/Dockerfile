#Etape 1 : Build l'application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#Etape2: Lancer l'application

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/Scripts/*.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8087
# Attends que la base soit prête avant de démarrer
ENTRYPOINT ["sh", "-c", "/wait-for-it.sh db:5432 -- java -jar app.jar"]
