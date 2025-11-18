# 📝 Cheatsheet — Annotations JPA (avec manipulation)

---

## 1. `@Entity` & `@Table`

### 📌 Définition

* `@Entity` → déclare une classe comme entité JPA (table DB).
* `@Table(name = "nom_table")` → optionnel, précise le nom de la table.

### 💻 Exemple

```java
@Entity
@Table(name = "playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
}
```

### 🛠️ Manipulation

```java
// Créer une playlist
Playlist p = new Playlist();
p.setTitre("Workout Mix");

// Sauvegarder avec le repository
playlistRepository.save(p);

// Récupérer
Playlist saved = playlistRepository.findById(1L).orElse(null);
System.out.println(saved.getTitre());
```

---

## 2. `@Id` & `@GeneratedValue`

### 📌 Définition

* `@Id` → clé primaire.
* `@GeneratedValue(strategy = …)` → auto-génération (IDENTITY = auto-incrément en PostgreSQL).

### 💻 Exemple

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

### 🛠️ Manipulation

```java
Playlist p = new Playlist();
p.setTitre("Chill Vibes");

Playlist saved = playlistRepository.save(p);
// L'ID est généré automatiquement
System.out.println(saved.getId()); // ex: 5
```

---

## 3. `@Column`

### 📌 Définition

Configure les colonnes (nom, contraintes).

### 💻 Exemple

```java
@Column(nullable = false, unique = true, length = 100)
private String titre;
```

### 🛠️ Manipulation

```java
// Essaie d'insérer deux playlists avec le même titre → erreur (unique)
playlistRepository.save(new Playlist("Unique Playlist"));
playlistRepository.save(new Playlist("Unique Playlist")); // ❌ ConstraintViolation
```

---

## 4. Relations (`@OneToMany`, `@ManyToOne`, `@ManyToMany`, `@OneToOne`)

### 📌 Définition

* **`@OneToMany`** → une playlist contient plusieurs morceaux.
* **`@ManyToOne`** → un morceau appartient à une playlist.
* **`@ManyToMany`** → un morceau peut être dans plusieurs playlists.
* **`@OneToOne`** → ex: utilisateur → profil.

### 💻 Exemple Playlist ↔ Song

```java
@Entity
public class Playlist {
    @Id @GeneratedValue
    private Long id;
    private String titre;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();
}

@Entity
public class Song {
    @Id @GeneratedValue
    private Long id;
    private String titre;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}
```

### 🛠️ Manipulation

```java
Playlist p = new Playlist();
p.setTitre("Road Trip");

Song s1 = new Song("Song A");
s1.setPlaylist(p);

Song s2 = new Song("Song B");
s2.setPlaylist(p);

p.getSongs().add(s1);
p.getSongs().add(s2);

// Sauvegarde en cascade
playlistRepository.save(p);

// Lecture
Playlist saved = playlistRepository.findById(p.getId()).get();
saved.getSongs().forEach(s -> System.out.println(s.getTitre()));
```

---

## 5. `@ElementCollection`

### 📌 Définition

Pour stocker des types simples ou `Map`.

### 💻 Exemple

```java
@ElementCollection
@CollectionTable(name = "playlist_tags", joinColumns = @JoinColumn(name = "playlist_id"))
@Column(name = "tag")
private List<String> tags = new ArrayList<>();
```

### 🛠️ Manipulation

```java
Playlist p = new Playlist();
p.setTitre("Focus");
p.getTags().add("concentration");
p.getTags().add("study");
playlistRepository.save(p);

// Lecture
Playlist saved = playlistRepository.findById(p.getId()).get();
System.out.println(saved.getTags()); // [concentration, study]
```

---

## 6. `@Enumerated`

### 📌 Définition

Mappe un `enum` en DB (`ORDINAL` = int, `STRING` = texte).

### 💻 Exemple

```java
public enum Genre { POP, ROCK, JAZZ }

@Enumerated(EnumType.STRING)
private Genre genre;
```

### 🛠️ Manipulation

```java
Song s = new Song();
s.setTitre("Imagine");
s.setGenre(Genre.ROCK);
songRepository.save(s);

// Lecture
Song saved = songRepository.findById(s.getId()).get();
System.out.println(saved.getGenre()); // ROCK
```

---

## 7. `@Transient`

### 📌 Définition

Champ non persisté (ignoré par DB).

### 💻 Exemple

```java
@Transient
private int nombreLikesTemporaire;
```

### 🛠️ Manipulation

```java
Song s = songRepository.findById(1L).get();
s.setNombreLikesTemporaire(42);
System.out.println(s.getNombreLikesTemporaire()); // 42
// Mais cette valeur ne sera pas sauvegardée en DB
```

---

## 8. `@Lob`

### 📌 Définition

Stocke un gros objet (texte long, fichiers).

### 💻 Exemple

```java
@Lob
private String paroles;
```

### 🛠️ Manipulation

```java
Song s = new Song();
s.setParoles("Texte très long...");
songRepository.save(s);
```

---

## ✅ Résumé amélioré

* **`@Entity` / `@Table`** → transforme ta classe en table → manipule via `Repository.save()` et `findById()`.
* **`@Id` / `@GeneratedValue`** → clé primaire auto-générée → récupérable après `save()`.
* **`@Column`** → contraintes (nullable, unique, length).
* **Relations (`@OneToMany`, etc.)** → manipuler les liens entre objets directement (`playlist.getSongs().add(song)`).
* **`@ElementCollection`** → listes/maps simples persistées.
* **`@Enumerated`** → enums mappés en DB.
* **`@Transient`** → ignoré par DB mais dispo en mémoire.
* **`@Lob`** → stocker du texte/fichier volumineux.

---

# 🧩 Cheatsheet — Annotations JPA / Hibernate

---

## ⚙️ 1️⃣ **Annotations de Base (déclaration d’une entité)**

| Annotation                | Explication                                                         | Exemple                                                    | Bonne pratique                                                             |
| ------------------------- | ------------------------------------------------------------------- | ---------------------------------------------------------- | -------------------------------------------------------------------------- |
| `@Entity`                 | Indique que la classe correspond à une **table en base**.           | `java @Entity public class Song { ... }`                   | Une seule entité par table. Toujours placer en haut de la classe.          |
| `@Table(name = "songs")`  | Permet de personnaliser le nom de la table.                         | `java @Entity @Table(name = "songs")`                      | Toujours expliciter le nom pour éviter les conflits (pluriel, snake_case). |
| `@Id`                     | Indique la **clé primaire** de l’entité.                            | `java @Id private Long id;`                                | Obligatoire dans chaque entité.                                            |
| `@GeneratedValue`         | Définit comment la clé primaire est générée (auto, sequence, etc.). | `java @GeneratedValue(strategy = GenerationType.IDENTITY)` | `IDENTITY` = auto-incrément (PostgreSQL, MySQL). `SEQUENCE` = Oracle.      |
| `@Column(name = "title")` | Lie un attribut à une colonne spécifique.                           | `java @Column(name="title", nullable=false)`               | Utile si le nom du champ diffère du nom de la colonne.                     |
| `@Transient`              | Champ non persisté (non stocké en DB).                              | `java @Transient private int tempScore;`                   | Utiliser pour des calculs temporaires.                                     |

---

## 🧱 2️⃣ **Annotations de Relations (Entre Entités)**

### 🔸 `@OneToOne`

| Concept                                | Explication                          | Exemple                                                                    |
| -------------------------------------- | ------------------------------------ | -------------------------------------------------------------------------- |
| Relation 1–1 (ex: un user a un profil) | Une seule instance liée à une autre. | `java @OneToOne @JoinColumn(name = "profile_id") private Profile profile;` |

**Bonne pratique** → toujours préciser `@JoinColumn`, sinon Hibernate crée une table de jointure inutile.

---

### 🔸 `@OneToMany` / `@ManyToOne`

| Concept            | Explication                                                                       | Exemple                                                                                                                                 |
| ------------------ | --------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| Relation 1–N / N–1 | Une playlist contient plusieurs chansons ; une chanson appartient à une playlist. | `java @OneToMany(mappedBy="playlist") private List<Song> songs;  @ManyToOne @JoinColumn(name="playlist_id") private Playlist playlist;` |

**Bonne pratique :**

* Utilise `mappedBy` du côté *inverse* (celui qui ne possède pas la clé étrangère).
* Active le `cascade = CascadeType.ALL` seulement si nécessaire.
* Utilise `fetch = FetchType.LAZY` pour éviter de charger toute la table à chaque requête.

---

### 🔸 `@ManyToMany`

| Concept                                                                                   | Explication                                   | Exemple                                                                                                                                                                     |
| ----------------------------------------------------------------------------------------- | --------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Relation N–N (ex: un artiste participe à plusieurs albums, un album a plusieurs artistes) | Crée une **table d’association automatique**. | `java @ManyToMany @JoinTable( name="artist_album", joinColumns=@JoinColumn(name="artist_id"), inverseJoinColumns=@JoinColumn(name="album_id") ) private Set<Album> albums;` |

**Bonne pratique :**

* Toujours nommer explicitement la table et les colonnes (`@JoinTable`).
* Utiliser `Set` plutôt que `List` pour éviter les doublons.

---

## ⚙️ 3️⃣ **Annotations de Configuration des Relations**

| Annotation                | Rôle                                                                             | Exemple                                                           | Bonne pratique                                                    |
| ------------------------- | -------------------------------------------------------------------------------- | ----------------------------------------------------------------- | ----------------------------------------------------------------- |
| `@JoinColumn(name="...")` | Définit la colonne de jointure (clé étrangère).                                  | `java @JoinColumn(name="playlist_id")`                            | Toujours préciser pour éviter les noms automatiques confus.       |
| `mappedBy`                | Indique le propriétaire inverse d’une relation bidirectionnelle.                 | `java @OneToMany(mappedBy="playlist")`                            | Toujours présent côté *inverse*.                                  |
| `cascade`                 | Détermine si les opérations sur une entité se répercutent sur les entités liées. | `java @OneToMany(cascade = CascadeType.ALL)`                      | ⚠️ À utiliser avec précaution — `ALL` peut supprimer massivement. |
| `fetch`                   | Stratégie de chargement des relations.                                           | `FetchType.LAZY` (par défaut pour @OneToMany) / `FetchType.EAGER` | Préférer LAZY sauf si besoin explicite immédiat.                  |

---

## 🧠 4️⃣ **Annotations d’Audit et Métadonnées**

| Annotation           | Rôle                                                                  | Exemple                                               | Bonne pratique                                                  |
| -------------------- | --------------------------------------------------------------------- | ----------------------------------------------------- | --------------------------------------------------------------- |
| `@CreationTimestamp` | Remplit automatiquement la date de création.                          | `@CreationTimestamp private LocalDateTime createdAt;` | Utiliser pour le suivi d’audit.                                 |
| `@UpdateTimestamp`   | Met à jour la date de modification.                                   | `@UpdateTimestamp private LocalDateTime updatedAt;`   | Idem pour les modifications.                                    |
| `@Version`           | Implémente le *pessimistic locking* (optimistic concurrency control). | `@Version private int version;`                       | À utiliser pour éviter les conflits de mise à jour concurrente. |

---

## 🧩 5️⃣ **Annotations spécifiques à Hibernate (non JPA mais utiles)**

| Annotation               | Explication                                          | Exemple                                                     | Bonne pratique                                       |
| ------------------------ | ---------------------------------------------------- | ----------------------------------------------------------- | ---------------------------------------------------- |
| `@DynamicUpdate`         | Hibernate ne met à jour que les colonnes modifiées.  | `@DynamicUpdate`                                            | Améliore les performances des gros objets.           |
| `@BatchSize(size = 10)`  | Hibernate charge les relations en batch (10 par 10). | `@OneToMany @BatchSize(size = 10)`                          | Utile pour éviter le “N+1 problem”.                  |
| `@Fetch(FetchMode.JOIN)` | Force une jointure SQL plutôt qu’une sous-requête.   | `@OneToMany(fetch = FetchType.LAZY) @Fetch(FetchMode.JOIN)` | À utiliser sur des relations fréquemment consultées. |

---

## 🧱 6️⃣ **Exemple complet – Entités Playlist / Song**

```java
@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Song> songs = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
}

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @CreationTimestamp
    private LocalDateTime addedAt;
}
```

---

## 🧠 7️⃣ **Résumé visuel des relations**

| Relation   | Exemple          | Table créée                              |
| ---------- | ---------------- | ---------------------------------------- |
| OneToOne   | User → Profile   | FK dans une table                        |
| OneToMany  | Playlist → Songs | FK côté Song                             |
| ManyToOne  | Songs → Playlist | FK côté Song                             |
| ManyToMany | Song ↔ Artist    | Table intermédiaire auto (`song_artist`) |

---

## 🚀 8️⃣ **Bonnes pratiques globales**

| Domaine     | Recommandation                                                                |
| ----------- | ----------------------------------------------------------------------------- |
| Performance | Toujours préférer `LAZY` loading par défaut.                                  |
| Clarté      | Toujours nommer les `@JoinColumn` et `@JoinTable`.                            |
| Suppression | Attention à `cascade = ALL` + `orphanRemoval = true`.                         |
| Test        | Toujours tester `save()`, `findAll()`, `delete()` sur une DB en mémoire (H2). |
| Migration   | Synchroniser avec Flyway ou Liquibase pour garder la DB propre.               |

---

Souhaites-tu que je te fasse maintenant la **version “diagramme UML” (PlantUML)**
→ qui montre clairement les relations `Playlist`, `Song`, `Artist`, `Album`, `User` avec les annotations correspondantes ?
Ça te permettrait de visualiser l’architecture de ton modèle JPA complet avant d’écrire le code.
