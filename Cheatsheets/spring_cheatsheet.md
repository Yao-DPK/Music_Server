# 📝 Cheatsheet Spring Boot (Java)

---

## 🔑 Concepts de base

* **Spring** : framework Java pour construire des applications (IoC, AOP, MVC, etc.).
* **Spring Boot** : version simplifiée de Spring → configuration automatique, serveur embarqué (Tomcat), dépendances faciles via *starters*.
* **IOC (Inversion of Control)** : au lieu de créer les objets toi-même (`new`), Spring les injecte quand il en a besoin.
* **Beans** : objets gérés par le conteneur Spring.
* **Annotations principales** :

  * `@SpringBootApplication` → point d’entrée d’une app.
  * `@RestController` → expose des endpoints REST.
  * `@Service` → logique métier.
  * `@Repository` → accès à la base de données.
  * `@Entity` → classe persistante (table DB).
  * `@Autowired` → injection de dépendances.

---

## 🔑 Bonnes Pratiques

- Laissez Spring gérer les versions des dépendances utilisées dans les projets: Ne pas renseigner le tag <`<version>`> dans le fichier pom.xml pour chaque dépendance(à moins que ce soit nécessaire). Cela facilite la simplicité et les mises à jour

---

## 🚀 Création d’un projet Spring Boot

### 1. Avec Spring Initializr (recommandé)

* Site : [https://start.spring.io](https://start.spring.io)
* Choix :

  * **Maven Project**
  * **Language**: Java
  * **Spring Boot version**: stable (3.x)
  * **Dependencies**:

    * Spring Web (API REST)
    * Spring Data JPA (accès DB)
    * PostgreSQL Driver
    * Lombok (facilite le code, optionnel)

Génère et télécharge → dézippe → ouvre dans ton IDE.

- maven central: Repo publoc ou on trouve les dépendances pour les projets Maven. On l'utilise ici pour récuperer les dépendances spring boot dont on a besoin
- Dépendances Usuelles:
  - spring-boot-starter-web: Ensemble des composant généralement utilisé pour lanceer un projet spring web

---

### 2. Arborescence standard

```
src/main/java/com/example/demo/
 ├── DemoApplication.java        # Point d’entrée
 ├── controller/                 # Expose les endpoints REST
 ├── service/                    # Logique métier
 ├── repository/                 # Accès DB
 └── model/                      # Entités (JPA)
```

---

### 3. Lancer l’application

```bash
mvn spring-boot:run
```

Puis accéder à `http://localhost:8080`.

---

## 📌 Exemple minimal (Hello World REST API)

### `DemoApplication.java`

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### `controller/HelloController.java`

```java
package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }
}
```

➡ Lance l’app → `GET http://localhost:8080/hello` renvoie `"Hello, Spring Boot!"`.

---

## 🗂️ Architecture en couches

### 1. **Model (Entités JPA)**

```java
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
}
```

### 2. **Repository**

```java
package com.example.demo.repository;

import com.example.demo.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {}
```

### 3. **Service**

```java
package com.example.demo.service;

import com.example.demo.model.Playlist;
import com.example.demo.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    private final PlaylistRepository repo;

    public PlaylistService(PlaylistRepository repo) {
        this.repo = repo;
    }

    public List<Playlist> findAll() {
        return repo.findAll();
    }

    public Playlist save(Playlist p) {
        return repo.save(p);
    }
}
```

### 4. **Controller**

```java
package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.service.PlaylistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @GetMapping
    public List<Playlist> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Playlist create(@RequestBody Playlist p) {
        return service.save(p);
    }
}
```

---

## 🛠️ Configuration DB (PostgreSQL)

Dans `application.properties` ou `application.yml` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/musique
spring.datasource.username=postgres
spring.datasource.password=tonmdp
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🔧 Build & Test

* **Build** :

```bash
mvn clean package
```

* **Tests unitaires (JUnit)** :

```java
@SpringBootTest
class DemoApplicationTests {
    @Test
    void contextLoads() {}
}
```

```bash
mvn test
```

---

## 📦 Packaging & Run (Jar)

```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## Notes

- Les DAO sont remplacés par les Repository JPA
- schema.sql ne sezra plus utilisé parce que Hibernate va gerer cela.
- La configuration JDBC n'est plus nécessaire avec JPA