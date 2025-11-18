# 🐳 Cheatsheet Docker

---

## 🔑 Concepts de base

* **Image** : modèle en lecture seule (ex: `openjdk:17`, `postgres:15`).
* **Container** : instance en cours d’exécution d’une image.
* **Dockerfile** : fichier qui décrit comment construire une image.
* **docker-compose** : outil pour orchestrer plusieurs conteneurs (ex: backend + DB).
* **Volume** : stockage persistant des données (DB, fichiers).
* **Network** : permet aux conteneurs de communiquer entre eux.

---

## 📌 Commandes essentielles

### Images

```bash
docker pull <image>               # télécharger une image
docker images                      # lister les images
docker rmi <image_id>              # supprimer une image
```

### Conteneurs

```bash
docker run -d --name myapp image   # lancer un conteneur détaché
docker ps                          # lister conteneurs actifs
docker ps -a                       # lister tous les conteneurs
docker stop <container_id>         # arrêter un conteneur
docker rm <container_id>           # supprimer un conteneur
```

### Logs & shell

```bash
docker logs -f <container_id>      # suivre logs
docker exec -it <container_id> sh  # ouvrir un shell
```

### Volumes & networks

```bash
docker volume ls                   # lister volumes
docker network ls                  # lister réseaux
```

---

## 🛠️ Dockerfile (Spring Boot)

```dockerfile
# Étape 1 : builder l’application
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : exécuter l’application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## ⚡ docker-compose (Spring Boot + PostgreSQL)

```yaml
version: "3.9"
services:
  db:
    image: postgres:15
    container_name: musique-db
    environment:
      POSTGRES_DB: musique
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: secret
    ports:
      - "5432:5432"
    volumes:
      - db-data:/var/lib/postgresql/data

  backend:
    build: .
    container_name: musique-api
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/musique
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: secret
    depends_on:
      - db

volumes:
  db-data:
```

👉 Ce fichier lance :

* Un conteneur `db` PostgreSQL, persistant grâce au volume.
* Un conteneur `backend` Spring Boot qui dépend de `db`.

---

## 📦 Commandes docker-compose

```bash
docker-compose up -d      # lancer les services en arrière-plan
docker-compose down       # arrêter et supprimer les conteneurs
docker-compose logs -f    # suivre les logs
docker-compose ps         # voir les services
```

---

## 🧩 Exemple pratique pour ton projet musique

1. Construire l’image backend :

```bash
docker build -t musique-api .
```

2. Lancer backend + DB :

```bash
docker-compose up -d
```

3. Vérifier :

* API → [http://localhost:8080](http://localhost:8080)
* DB → accessible sur port `5432`

---

Bonne question 👌
Pour vraiment **maîtriser Docker**, il faut connaître le cycle de vie standard qu’on répète dans quasiment tous les projets.

---

# 🚀 Étapes usuelles d’utilisation de Docker

## 1. **Installer et configurer Docker**

* Installer Docker Engine (`docker`) et Docker Compose (`docker compose`).
* Vérifier l’installation :

  ```bash
  docker --version
  docker compose version
  ```

---

## 2. **Récupérer une image existante**

* Télécharger (pull) une image depuis Docker Hub :

  ```bash
  docker pull postgres:15
  ```
* Vérifier les images locales :

  ```bash
  docker images
  ```

---

## 3. **Lancer un conteneur**

* Démarrer un conteneur simple :

  ```bash
  docker run -d --name mydb -p 5432:5432 -e POSTGRES_PASSWORD=pass postgres:15
  ```
* Vérifier les conteneurs en cours :

  ```bash
  docker ps
  ```

---

## 4. **Construire sa propre image**

* Écrire un `Dockerfile` (instructions pour créer ton image) :

  ```dockerfile
  FROM openjdk:17-jdk-slim
  COPY target/mvp.jar app.jar
  ENTRYPOINT ["java","-jar","/app.jar"]
  ```
* Construire l’image :

  ```bash
  docker build -t music-server:1.0 .
  ```

---

## 5. **Lancer ton image**

* Démarrer ton app dans un conteneur basé sur l’image que tu viens de créer :

  ```bash
  docker run -d --name music_app -p 8080:8080 music-server:1.0
  ```

---

## 6. **Utiliser Docker Compose (multi-services)**

* Définir plusieurs services (app + DB + cache, etc.) dans un fichier `docker-compose.yml` :

  ```yaml
  version: '3.8'
  services:
    db:
      image: postgres:15
      environment:
        POSTGRES_USER: pyke_music
        POSTGRES_PASSWORD: secret
        POSTGRES_DB: musicdb
      ports:
        - "5432:5432"

    app:
      build: .
      ports:
        - "8080:8080"
      depends_on:
        - db
  ```
* Lancer le tout :

  ```bash
  docker compose up -d
  ```

---

## 7. **Inspecter et gérer**

* Voir logs :

  ```bash
  docker logs music_app
  ```
* Entrer dans un conteneur :

  ```bash
  docker exec -it music_app bash
  ```
* Stopper / supprimer :

  ```bash
  docker stop music_app
  docker rm music_app
  ```

---

## 8. **Optimiser et déployer**

* Créer un `.dockerignore` pour éviter d’envoyer inutilement `target/`, `.git/`, etc.
* Pousser ton image vers Docker Hub :

  ```bash
  docker tag music-server:1.0 pyke/music-server:1.0
  docker push pyke/music-server:1.0
  ```

---

# 📌 Résumé du cycle

1. **Pull** → récupérer une image
2. **Run** → lancer un conteneur
3. **Dockerfile** → créer ta propre image
4. **Build** → construire ton image
5. **Run** → lancer ton app avec ton image
6. **Compose** → orchestrer plusieurs services
7. **Inspect/Logs** → debugger
8. **Push** → partager ton image

---

👉 Tu veux que je te fasse un **cheatsheet Docker Markdown** avec toutes ces commandes prêtes à l’emploi comme tu m’avais demandé pour Git et Spring ?
