
---

# 🧭 Cheatsheet — REST API

---

## ⚙️ 1️⃣ **Concepts Communs Détaillés**

| Concept                               | Explication                                                                                                     | Exemple                                                                               | Bonne pratique                                                                                          |
| ------------------------------------- | --------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| **Ressource**                         | Une entité manipulable via HTTP (ex : Song, Playlist, User). Chaque ressource a un **identifiant unique** (ID). | `/songs/42` → représente la chanson avec l’ID 42.                                     | Utiliser des **noms pluriels** pour les ressources : `/songs`, `/users`, `/playlists`.                  |
| **Endpoint**                          | L’URL publique qui permet d’accéder à une ressource ou une action.                                              | `GET /songs/{id}`                                                                     | Nommer les endpoints de manière claire et descriptive.                                                  |
| **HTTP Verbs (Méthodes)**             | Indiquent l’intention de l’opération : récupérer, créer, modifier, supprimer.                                   | `GET`, `POST`, `PUT`, `PATCH`, `DELETE`                                               | Respecter la sémantique REST : GET = lecture, POST = création, PUT = mise à jour, DELETE = suppression. |
| **URI (Uniform Resource Identifier)** | L’adresse d’une ressource. Elle doit être stable, lisible et hiérarchique.                                      | `/users/5/playlists/10/songs`                                                         | Pas de verbes dans les URI (`/createUser` ❌ → `/users` ✅).                                              |
| **Représentation JSON**               | Les ressources sont échangées sous format JSON (souvent).                                                       | `{ "id": 1, "title": "Palmtree Panic" }`                                              | JSON camelCase, éviter d’exposer des IDs inutiles.                                                      |
| **Statelessness**                     | Chaque requête contient toutes les infos nécessaires (pas de session persistante côté serveur).                 | Chaque requête contient le token d’authentification (`Authorization: Bearer …`)       | Éviter les dépendances serveur : pas de session state, tout doit être déterminé par la requête.         |
| **CRUD**                              | Les 4 opérations de base : Create, Read, Update, Delete.                                                        | `POST`, `GET`, `PUT/PATCH`, `DELETE`                                                  | Toujours renvoyer le bon code HTTP associé.                                                             |
| **HATEOAS** *(optionnel)*             | Hypermedia as the Engine of Application State — inclure les liens d’action possibles dans la réponse.           | `{ "id": 1, "title": "Song", "links": { "self": "/songs/1", "album": "/albums/10" }}` | Utile pour APIs publiques, pas obligatoire pour MVP.                                                    |
| **Versioning**                        | Gérer plusieurs versions d’API sans casser les anciennes.                                                       | `/api/v1/songs` ou header `Accept: application/vnd.app.v1+json`                       | Préférer `/api/v1/` dans les URI.                                                                       |

---

## 🧩 2️⃣ **Méthodes HTTP et Cas d’Utilisation**

| Méthode    | Usage                                                | Exemple                                            | Bonne pratique                                                                    |
| ---------- | ---------------------------------------------------- | -------------------------------------------------- | --------------------------------------------------------------------------------- |
| **GET**    | Lire une ressource ou une liste de ressources.       | `GET /songs` / `GET /songs/42`                     | Pas de corps dans la requête. Renvoyer `200 OK` si succès.                        |
| **POST**   | Créer une nouvelle ressource.                        | `POST /songs` avec `{ "title": "Palmtree Panic" }` | Renvoyer `201 Created` + header `Location: /songs/{id}`                           |
| **PUT**    | Remplacer complètement une ressource existante.      | `PUT /songs/42`                                    | Renvoyer `200 OK` si succès. Ne pas oublier de renvoyer la ressource mise à jour. |
| **PATCH**  | Modifier partiellement une ressource.                | `PATCH /songs/42` avec `{ "title": "New Name" }`   | Utiliser quand tu veux juste changer un champ.                                    |
| **DELETE** | Supprimer une ressource.                             | `DELETE /songs/42`                                 | Renvoyer `204 No Content`. Pas de corps dans la réponse.                          |
| **HEAD**   | Même chose que GET mais sans le corps de la réponse. | `HEAD /songs`                                      | Permet de tester l’existence d’une ressource.                                     |

---

## 📦 3️⃣ **Structure typique d’un REST Controller (Spring Boot)**

```java
@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Song>> getSongsByTitle(@RequestParam(required = false) String title) {
        if (title != null) return ResponseEntity.ok(songService.findByTitle(title));
        return ResponseEntity.ok(songService.findAll());
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song created = songService.save(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 🔐 4️⃣ **Codes de Réponse HTTP**

| Code                        | Signification              | Utilisation                        |
| --------------------------- | -------------------------- | ---------------------------------- |
| `200 OK`                    | Succès standard.           | Lecture, modification.             |
| `201 Created`               | Ressource créée.           | POST réussi.                       |
| `204 No Content`            | Suppression réussie.       | DELETE réussi.                     |
| `400 Bad Request`           | Requête invalide.          | Données manquantes ou invalides.   |
| `401 Unauthorized`          | Authentification requise.  | Token invalide ou manquant.        |
| `403 Forbidden`             | Refus d’accès.             | L’utilisateur n’a pas les droits.  |
| `404 Not Found`             | Ressource inexistante.     | ID ou critère inexistant.          |
| `409 Conflict`              | Conflit dans la requête.   | Ressource déjà existante, doublon. |
| `500 Internal Server Error` | Erreur serveur inattendue. | Exception non gérée.               |

---

## 🧱 5️⃣ **Bonnes Pratiques REST**

| Thème          | Bonne pratique                              | Exemple                                                    |
| -------------- | ------------------------------------------- | ---------------------------------------------------------- |
| **Nommage**    | Noms de ressources au pluriel, sans verbes. | `/songs`, `/playlists`, `/users`.                          |
| **Hiérarchie** | Refléter les relations dans l’URL.          | `/users/{id}/playlists/{pid}`                              |
| **Filtres**    | Utiliser `?param=value` pour les filtres.   | `/songs?artist=Daft%20Punk`                                |
| **Pagination** | Retourner les pages de résultats.           | `/songs?page=1&size=20`                                    |
| **Tri**        | Autoriser le tri via un paramètre.          | `/songs?sort=title,asc`                                    |
| **Validation** | Vérifier les entrées utilisateur.           | `@Valid` sur les DTOs.                                     |
| **Erreurs**    | Toujours renvoyer un JSON d’erreur clair.   | `{ "error": "Not Found", "message": "Song 42 not found" }` |
| **Sécurité**   | Utiliser JWT ou OAuth2.                     | Header `Authorization: Bearer …`                           |
| **Versioning** | Prévoir `/api/v1/...`                       | `/api/v1/songs`                                            |

---

## 🧠 6️⃣ **Exemples concrets dans ton contexte musique**

| Cas                                      | Endpoint                              | Type   | Explication                                 |
| ---------------------------------------- | ------------------------------------- | ------ | ------------------------------------------- |
| 🎵 Obtenir une chanson                   | `GET /songs/{id}`                     | GET    | Récupère une chanson par son ID (technique) |
| 🔍 Rechercher une chanson par titre      | `GET /songs?title=Palmtree Panic`     | GET    | Recherche métier                            |
| 🧑‍💻 Créer une playlist                 | `POST /playlists`                     | POST   | Crée une playlist                           |
| ➕ Ajouter un morceau à une playlist      | `POST /playlists/{id}/songs`          | POST   | Ajoute un lien entre playlist et chanson    |
| ❌ Supprimer une chanson d’une playlist   | `DELETE /playlists/{pid}/songs/{sid}` | DELETE | Supprime la relation                        |
| 📜 Lister toutes les playlists d’un user | `GET /users/{id}/playlists`           | GET    | Lecture relationnelle                       |
| 🎧 Jouer une chanson                     | `POST /player/play/{songId}`          | POST   | Action spécifique                           |

---

## ⚙️ 7️⃣ **Checklist avant de livrer ton API**

✅ Tes endpoints utilisent bien les **bons verbes HTTP**
✅ Tes URI sont **claires, hiérarchiques et cohérentes**
✅ Tu renvoies **les bons codes HTTP**
✅ Tes erreurs ont un **format JSON uniforme**
✅ Tu respectes **statelessness**
✅ Tu testes tes endpoints avec **Postman / cURL**
✅ Tu documentes ton API (Swagger / OpenAPI)

---