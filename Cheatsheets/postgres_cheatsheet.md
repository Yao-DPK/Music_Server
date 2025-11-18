# 🐘 Cheatsheet PostgreSQL

---

## 🔑 1. Installation

### 📍 Installation locale

#### Sur **Linux (Ubuntu/Debian)**

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
```

#### Sur **MacOS (brew)**

```bash
brew install postgresql
brew services start postgresql
```

#### Sur **Windows**

* Télécharger l’installateur sur 👉 [https://www.postgresql.org/download/windows/](https://www.postgresql.org/download/windows/)
* Pendant l’installation : définir **mot de passe admin (postgres)**.
* Installer aussi **pgAdmin** (GUI pour gérer la DB).

---

### 📍 Installation avec Docker

Créer un fichier `docker-compose.yml` :

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    container_name: postgres_container
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: musicdb
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

Lancer PostgreSQL :

```bash
docker-compose up -d
```

Se connecter :

```bash
docker exec -it postgres_container psql -U postgres -d musicdb
```

Parfait 👌 tu veux enrichir ton **cheatsheet PostgreSQL** avec les **commandes CLI de `psql`**.
Je vais compléter ce qu’on avait fait avec une **section dédiée**, claire et pratique.

---

## 2.Commandes `psql` (administration CLI)



### 🔑 1. Connexion à `psql`

```bash
psql -U postgres -d musicdb -h localhost -p 5432
```

* `-U` → utilisateur (ex: postgres, music_user)
* `-d` → base de données (optional)
* `-h` → hôte (localhost par défaut)
* `-p` → port (5432 par défaut) (optional)



### 🔑 2. Commandes `psql` (meta-commandes)

Ces commandes **commencent toujours par `\`** et ne sont pas du SQL standard.

| Commande      | Explication                                          |
| ------------- | ---------------------------------------------------- |
| `\q`          | Quitter psql                                         |
| `\l`          | Lister toutes les bases                              |
| `\c dbname`   | Se connecter à une base                              |
| `\dt`         | Lister toutes les tables                             |
| `\d table`    | Décrire la structure d’une table                     |
| `\di`         | Lister les index                                     |
| `\du`         | Lister les rôles (utilisateurs)                      |
| `\dn`         | Lister les schémas                                   |
| `\df`         | Lister les fonctions                                 |
| `\dv`         | Lister les vues                                      |
| `\x`          | Activer/désactiver l’affichage étendu (plus lisible) |
| `\timing`     | Activer le chronomètre des requêtes                  |
| `\! commande` | Exécuter une commande shell depuis psql (`\! ls`)    |



### 🔑 3. Commandes SQL utiles depuis `psql`

#### Base de données

```sql
CREATE DATABASE musicdb;
DROP DATABASE musicdb;
```

#### Utilisateurs et rôles

```sql
CREATE USER music_user WITH PASSWORD 'secret';
ALTER USER postgres WITH ENCRYPTED PASSWORD 'newpass';
DROP USER music_user;
```

#### Tables

```sql
CREATE TABLE playlist (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL
);

\d playlist   -- voir la structure
```

#### Droits

```sql
GRANT ALL PRIVILEGES ON DATABASE musicdb TO music_user;
REVOKE DELETE ON ALL TABLES IN SCHEMA public FROM music_user;
```



### 🔑 4. Astuces pratiques en `psql`

* **Auto-complétion** : touche `TAB`.
* **Historique** : flèches ↑ et ↓.
* **Rejouer une commande** : `\s` liste l’historique, `\e` ouvre l’éditeur.
* **Changer de base** sans quitter :

  ```sql
  \c autre_db autre_user
  ```


##### ✅ Exemple de workflow rapide

```bash
# Connexion
psql -U postgres -d musicdb

# Lister les DB
\l

# Basculer sur musicdb
\c musicdb

# Lister les tables
\dt

# Voir structure d'une table
\d song

# Créer une table
CREATE TABLE test (id SERIAL PRIMARY KEY, name TEXT);

# Quitter
\q
```

## 🔑 3. SQL de base

### Création / Suppression de DB

```sql
CREATE DATABASE musicdb;
DROP DATABASE musicdb;
```

### Création d’utilisateur

```sql
CREATE USER music_user WITH PASSWORD 'secret';
GRANT ALL PRIVILEGES ON DATABASE musicdb TO music_user;
```

### Tables

```sql
CREATE TABLE playlist (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE song (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    artiste VARCHAR(100),
    playlist_id INT REFERENCES playlist(id)
);
```

### Insertion

```sql
INSERT INTO playlist (titre, description) VALUES ('Road Trip', 'Playlist pour voyager');
INSERT INTO song (titre, artiste, playlist_id) VALUES ('Song A', 'Artiste 1', 1);
```

### Lecture

```sql
SELECT * FROM playlist;
SELECT * FROM song WHERE playlist_id = 1;
```

### Mise à jour

```sql
UPDATE playlist SET titre = 'Summer Vibes' WHERE id = 1;
```

### Suppression

```sql
DELETE FROM song WHERE id = 2;
```

---

## 🔑 4. Gestion des droits

```sql
GRANT SELECT, INSERT, UPDATE ON ALL TABLES IN SCHEMA public TO music_user;
REVOKE DELETE ON ALL TABLES IN SCHEMA public FROM music_user;
```

---

## 🔑 5. Connexion depuis Spring Boot (`application.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/musicdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

# ✅ Résumé rapide

* **Local** : installer avec `apt`, `brew`, ou Windows installer.
* **Docker** : `docker-compose up -d` et exposer sur `5432`.
* **psql** : `\l`, `\c`, `\dt`, `\d`, `\q`.
* **SQL** : `CREATE DATABASE`, `CREATE TABLE`, `INSERT`, `SELECT`, `UPDATE`, `DELETE`.
* **Spring Boot** : configure `application.properties` avec URL + user + password.

---

Excellente question 👌 tu ne veux pas juste le **fix rapide**, mais comprendre le **fond du problème** : pourquoi PostgreSQL réagit comme ça, et ce qu’il faut savoir en général sur son système d’authentification.

---

# 🐘 Petit cours : Authentification PostgreSQL (et pourquoi ton erreur apparaît)

---

## 🔑 1. Comment PostgreSQL gère l’authentification ?

PostgreSQL est un **SGBD multi-utilisateurs** → chaque connexion doit être authentifiée.
Il utilise deux notions :

1. **Utilisateur système Linux** (ex: `pyke`, `postgres`)
2. **Rôle PostgreSQL** (utilisateur interne du SGBD, ex: `postgres`, `music_user`)

⚠️ Les deux ne sont pas liés automatiquement → sauf avec le mode `peer`.

---

## 🔑 2. Le fichier `pg_hba.conf`

C’est le fichier **Host-Based Authentication**, situé généralement dans :

```
/etc/postgresql/<version>/main/pg_hba.conf
```

Il contrôle **qui peut se connecter, d’où, et comment**.

Format :

```
TYPE    DATABASE    USER         ADDRESS        METHOD
```

* **TYPE** → `local` (socket Unix), `host` (TCP/IP)
* **DATABASE** → base concernée (ou `all`)
* **USER** → rôle PostgreSQL concerné (ou `all`)
* **ADDRESS** → IP autorisée (`127.0.0.1/32`, `0.0.0.0/0`, etc.)
* **METHOD** → méthode d’authentification (`peer`, `md5`, `scram-sha-256`, `trust`...)

---

## 🔑 3. Méthodes d’authentification

### `peer`

* Vérifie que l’utilisateur Linux **porte le même nom** que le rôle PostgreSQL.
* Ex : si tu es connecté en tant que `postgres` sous Linux, tu peux accéder au rôle `postgres` dans PostgreSQL → sans mot de passe.
* C’est pour ça que sous Ubuntu tu dois faire :

  ```bash
  sudo -u postgres psql
  ```

### `md5`

* Authentification par mot de passe hashé en **MD5**.
* Permet à n’importe quel utilisateur de se connecter avec `-U user -h localhost -W`.
* C’est le mode le plus utilisé avec les applications (ex: Spring Boot).

### `scram-sha-256`

* Authentification par mot de passe hashé en **SCRAM-SHA-256** (plus moderne que MD5).
* À privilégier si possible (Postgres 10+).

### `trust`

* Connexion sans mot de passe ni vérification (⚠️ dangereux).
* Utile seulement pour des tests rapides en local.

---

## 🔑 4. Pourquoi ton erreur ?

Ton erreur :

```
FATAL: Peer authentication failed for user "postgres"
```

👉 PostgreSQL a vu que dans `pg_hba.conf` tu as une ligne comme :

```
local   all   postgres   peer
```

Ça veut dire :

* Si tu veux te connecter au rôle `postgres` → il faut que ton utilisateur **Linux** s’appelle aussi `postgres`.
* Or toi tu es `pyke` → donc rejet.

---

## 🔑 5. Bonnes pratiques

1. **N’utilise pas `peer` pour les apps (Spring Boot, etc.)**

   * Car ton appli ne peut pas "se faire passer" pour l’utilisateur Linux.
   * Utilise plutôt `md5` ou `scram-sha-256`.

2. **Crée un utilisateur dédié par application**

   * Ex : `music_user` pour ton serveur de musique.
   * Avec mot de passe, droits limités (pas superuser).

3. **N’utilise `postgres` (superadmin) que pour l’administration**

   * C’est l’équivalent de `root`.
   * Ton app n’a pas besoin de super pouvoirs.

4. **Sépare les configs locales vs distantes**

   * Local → `host    all   all   127.0.0.1/32   md5`
   * Distant → ouvre uniquement aux IPs nécessaires.

---

## 🔑 6. Exemple de config `pg_hba.conf` propre

```
# Autoriser postgres en local (admin seulement)
local   all             postgres                                peer

# Autoriser un utilisateur applicatif en local via mot de passe
local   all             music_user                              md5

# Autoriser depuis localhost en TCP/IP
host    all             music_user      127.0.0.1/32            md5

# (optionnel) Autoriser depuis ton réseau privé
host    musicdb         music_user      192.168.1.0/24          md5
```

---

# ✅ Résumé du cours

* PostgreSQL gère les connexions via **pg_hba.conf**.
* Ton erreur vient du fait que `postgres` est configuré en `peer` → tu dois être loggué en tant qu’utilisateur Linux `postgres`.
* Pour ton projet Spring Boot → configure plutôt un utilisateur dédié (`music_user`) avec `md5` ou `scram-sha-256`.


Parfait 👌 tu touches maintenant au **problème le plus fréquent** après avoir créé un utilisateur PostgreSQL.

Ton erreur :

```
psql: error: connection to server on socket "/var/run/postgresql/.s.PGSQL.5432" failed: 
FATAL:  database "pyke_music" does not exist
```

---

# 🔎 Pourquoi ça arrive ?

* Quand tu te connectes avec :

  ```bash
  psql -U pyke_music
  ```

  et que tu **n’indiques pas de base (`-d`)**, PostgreSQL essaie par défaut de se connecter à une **base qui porte le même nom que l’utilisateur** (`pyke_music`).

* Ici, tu as créé un **utilisateur `pyke_music`**, mais **pas encore de base de données `pyke_music`**.
  👉 PostgreSQL dit donc : *"la base `pyke_music` n’existe pas"*.

---

# ✅ Solutions

### Option 1 – Se connecter à une autre base (par ex `postgres`)

```bash
psql -U pyke_music -d postgres
```

👉 Tu seras connecté avec l’utilisateur `pyke_music`, mais sur la base `postgres` (qui existe toujours par défaut).

---

### Option 2 – Créer une base avec le même nom que l’utilisateur

Pour suivre la convention Postgres (chaque user a une DB par défaut) :

```sql
-- Connecte-toi en superuser
psql -U postgres

-- Crée la base pour ton user
CREATE DATABASE pyke_music OWNER pyke_music;
\q
```

Puis :

```bash
psql -U pyke_music -d pyke_music
```

👉 Ça fonctionnera.

---

### Option 3 – Créer une base applicative dédiée (recommandée pour Spring Boot)

Exemple pour ton projet musique :

```sql
-- Depuis le superuser
CREATE DATABASE musicdb OWNER pyke_music;
GRANT ALL PRIVILEGES ON DATABASE musicdb TO pyke_music;
```

Puis connexion :

```bash
psql -U pyke_music -d musicdb
```

Et côté Spring Boot → `application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/musicdb
spring.datasource.username=pyke_music
spring.datasource.password=TonMotDePasse
```

---

# 🚀 Recommandation pour ton projet

* Garde `postgres` uniquement pour l’**administration**.
* Crée un **utilisateur dédié (`pyke_music`)** avec sa **propre base (`musicdb`)**.
* Utilise cet utilisateur et cette base dans Spring Boot.

---


# ✅ PostgreSQL Mastery Checklist

## 1. Débutant – "Je connais PostgreSQL"

- [ ] **Installation locale (Linux, Windows, Mac, Docker)**  
  → Être capable d’installer Postgres sur n’importe quel OS ou via Docker Compose et lancer un service fonctionnel.

- [ ] **Connexion avec `psql`**  
  → Savoir se connecter avec `psql -U user -d db -h localhost` et comprendre les options de connexion.

- [ ] **Commandes `psql` de base**  
  → Utiliser `\l` (bases), `\c` (changer de base), `\dt` (tables), `\du` (utilisateurs), `\d` (structure d’une table).

- [ ] **Bases par défaut (`postgres`, `template0`, `template1`)**  
  → Savoir à quoi elles servent : `postgres` pour l’admin, `template0` comme base vierge, `template1` comme modèle cloné.

- [ ] **SQL basique (CRUD)**  
  → Écrire `CREATE TABLE`, `INSERT`, `SELECT`, `UPDATE`, `DELETE` sans erreur.

- [ ] **Types de données**  
  → Connaître les types standards (`int`, `varchar`, `text`, `boolean`, `date`, `timestamp`) et quand les utiliser.

---

## 2. Intermédiaire – "Je suis à l’aise avec PostgreSQL"

- [ ] **Gestion des utilisateurs**  
  → Créer des utilisateurs avec mot de passe (`CREATE USER … WITH ENCRYPTED PASSWORD`) et comprendre la différence avec un compte Linux.

- [ ] **Gestion des droits**  
  → Utiliser `GRANT` et `REVOKE` pour donner/retirer des permissions sur DB, tables et schémas.

- [ ] **pg_hba.conf & méthodes d’auth**  
  → Comprendre `peer`, `md5`, `scram-sha-256`, leur usage et modifier `pg_hba.conf` correctement.

- [ ] **Relations entre tables**  
  → Définir des clés primaires/étrangères, utiliser `ON DELETE CASCADE` et garantir l’intégrité référentielle.

- [ ] **Index simples**  
  → Créer des index (`CREATE INDEX`) et comprendre qu’ils accélèrent les recherches au prix de plus d’écritures.

- [ ] **Transactions**  
  → Utiliser `BEGIN`, `COMMIT`, `ROLLBACK` et comprendre pourquoi elles garantissent l’atomicité.

- [ ] **Vues et vues matérialisées**  
  → Créer une vue (`CREATE VIEW`) pour simplifier une requête, et une vue matérialisée pour booster la performance.

- [ ] **Sauvegarde et restauration**  
  → Faire un `pg_dump` et restaurer avec `psql`, comprendre la différence avec un backup binaire.

---

## 3. Avancé – "Je maîtrise PostgreSQL"

- [ ] **Extensions**  
  → Installer et utiliser des extensions comme `uuid-ossp`, `pgcrypto`, `postgis` pour enrichir PostgreSQL.

- [ ] **Optimisation de requêtes**  
  → Lire un `EXPLAIN` / `EXPLAIN ANALYZE` et comprendre le plan d’exécution pour détecter les goulots d’étranglement.

- [ ] **Index avancés**  
  → Connaître B-Tree, Hash, GIN, GiST, BRIN et savoir lequel utiliser selon le type de données.

- [ ] **Partitionnement**  
  → Créer des tables partitionnées (`PARTITION BY`) et comprendre quand c’est utile (gros volumes de données).

- [ ] **Fonctions & PL/pgSQL**  
  → Écrire des fonctions stockées (`CREATE FUNCTION`) et des procédures métier côté base.

- [ ] **Triggers**  
  → Définir un `CREATE TRIGGER` pour exécuter une action automatiquement lors d’un `INSERT`, `UPDATE` ou `DELETE`.

- [ ] **Concurrence & transactions**  
  → Comprendre les niveaux d’isolation (`READ COMMITTED`, `REPEATABLE READ`, `SERIALIZABLE`) et le MVCC.

- [ ] **Monitoring interne**  
  → Interroger `pg_stat_activity`, `pg_stat_user_tables` pour voir l’activité et l’état des connexions.

- [ ] **Maintenance**  
  → Utiliser `VACUUM`, `ANALYZE`, `REINDEX` pour entretenir et optimiser la base.

---

## 4. Expert – "Je domine PostgreSQL"

- [ ] **Tuning du serveur (`postgresql.conf`)**  
  → Ajuster `work_mem`, `shared_buffers`, `max_connections`, `autovacuum` pour optimiser les perfs.

- [ ] **Sécurité avancée**  
  → Configurer SSL/TLS, utiliser `pg_hba.conf` de façon granulaire, créer des rôles sans login (`NOLOGIN`).

- [ ] **Haute disponibilité**  
  → Mettre en place la réplication en streaming, la réplication logique, ou utiliser Patroni/Pgpool-II.

- [ ] **Sauvegardes avancées**  
  → Faire des sauvegardes incrémentales avec `pg_basebackup`, utiliser la restauration Point-In-Time (PITR).

- [ ] **Supervision & metrics**  
  → Utiliser `pg_stat_statements` et brancher Postgres à Prometheus/Grafana.

- [ ] **Scalabilité horizontale**  
  → Comprendre le sharding, la réplication multi-maîtres, l’architecture multi-tenant.

- [ ] **Contribuer à l’écosystème**  
  → Être capable d’installer, configurer et même contribuer à des projets autour de PostgreSQL comme PostGIS, TimescaleDB.

