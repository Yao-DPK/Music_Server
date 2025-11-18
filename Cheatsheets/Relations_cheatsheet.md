

### 💾 Relations UML ↔ Clé étrangère ↔ Annotations JPA

---

## 🧱 1️⃣ Principe général

| Relation UML           | Clé étrangère située dans... | Côté “propriétaire” en JPA | Annotations principales      |
| ---------------------- | ---------------------------- | -------------------------- | ---------------------------- |
| **1 → 1 (OneToOne)**   | l’une des deux tables        | Le côté qui a la FK        | `@OneToOne`, `@JoinColumn`   |
| **1 → * (OneToMany)**  | table du côté * (Many)       | le côté * (Many)           | `@ManyToOne` + `@JoinColumn` |
| *** → 1 (ManyToOne)**  | table du côté “Many”         | le côté “Many”             | `@ManyToOne` + `@JoinColumn` |
| *** ↔ * (ManyToMany)** | table intermédiaire          | aucune entité directement  | `@ManyToMany` + `@JoinTable` |

---

## 🧩 2️⃣ Lecture directionnelle UML

> 🧭 **La flèche UML (`-->`) pointe vers le côté “non-propriétaire”**,
> c’est-à-dire celui qui **n’a pas** la clé étrangère.

Exemple :

```plantuml
User "1" --> "*" Song : possède
```

📘 Interprétation :

* Un `User` possède plusieurs `Song`.
* C’est `Song` qui stocke la clé étrangère (`user_id`).

---

## 🧠 3️⃣ Exemples concrets avec JPA et SQL

### 🟢 **OneToMany / ManyToOne**

```plantuml
User "1" --> "*" Song
```

**Java**

```java
@Entity
public class User {
  @Id @GeneratedValue private Long id;
  private String nom;

  @OneToMany(mappedBy="owner")   // côté inverse
  private List<Song> songs;
}

@Entity
public class Song {
  @Id @GeneratedValue private Long id;
  private String titre;

  @ManyToOne                 // côté propriétaire
  @JoinColumn(name="user_id")
  private User owner;
}
```

**SQL**

```sql
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  nom TEXT
);

CREATE TABLE songs (
  id BIGSERIAL PRIMARY KEY,
  titre TEXT,
  user_id BIGINT REFERENCES users(id)
);
```

---

### 🟣 **OneToOne**

```plantuml
User "1" --> "1" Profile
```

**Java**

```java
@Entity
public class User {
  @Id @GeneratedValue private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_id")  // clé étrangère ici
  private Profile profile;
}

@Entity
public class Profile {
  @Id @GeneratedValue private Long id;
  private String bio;
}
```

**SQL**

```sql
CREATE TABLE profiles (
  id BIGSERIAL PRIMARY KEY,
  bio TEXT
);

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  profile_id BIGINT REFERENCES profiles(id)
);
```

---

### 🟠 **ManyToMany**

```plantuml
User "*" --> "*" Role
```

**Java**

```java
@Entity
public class User {
  @Id @GeneratedValue private Long id;

  @ManyToMany
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;
}

@Entity
public class Role {
  @Id @GeneratedValue private Long id;
  private String name;
}
```

**SQL**

```sql
CREATE TABLE user_roles (
  user_id BIGINT REFERENCES users(id),
  role_id BIGINT REFERENCES roles(id),
  PRIMARY KEY (user_id, role_id)
);
```

---

## ⚙️ 4️⃣ Tableau de repérage rapide

| Relation | UML                 | FK dans quelle table ? | JPA (propriétaire)         | Annotation côté inverse       |
| -------- | ------------------- | ---------------------- | -------------------------- | ----------------------------- |
| 1→1      | flèche vers l’autre | une des deux           | `@OneToOne + @JoinColumn`  | `@OneToOne(mappedBy="...")`   |
| 1→*      | flèche vers *       | *                      | `@ManyToOne + @JoinColumn` | `@OneToMany(mappedBy="...")`  |
| *→1      | flèche vers 1       | *                      | `@ManyToOne + @JoinColumn` | `@OneToMany(mappedBy="...")`  |
| *↔*      | double sens         | table intermédiaire    | `@ManyToMany + @JoinTable` | `@ManyToMany(mappedBy="...")` |

---

## 🧠 5️⃣ Astuce de lecture “senior”

> ✅ **Règle mentale simple :**
> Le côté *“Many”* est **presque toujours celui qui contient la clé étrangère.**
>
> 🔁 Le côté “One” est celui qui est référencé (souvent via `mappedBy`).

---
