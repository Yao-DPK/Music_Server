# ⚙️ Cheatsheet GitHub Actions
---

## 🔑 Mots-clés

- **workflow** : le fichier YAML qui décrit ton automatisation.
- **job**: un groupe d’étapes (steps) qui s’exécutent sur une machine.
- **step**: une action unique (ex: installer Java, lancer des tests).
- **action**: une brique réutilisable (ex: actions/checkout clone ton repo).
- **artifact**: un fichier généré que tu sauvegardes (ex: PDF, build).
- **runner**: la machine (Ubuntu, Windows, MacOS) qui exécute ton workflow.
---

## 📌 Structure d’un workflow

```yaml

name: NomDuWorkflow

on: [push, pull_request]   # événements déclencheurs

jobs:
  build:                   # nom du job
    runs-on: ubuntu-latest # type de runner
    steps:                 # étapes
      - name: Checkout repo
        uses: actions/checkout@v3

      - name: Run a script
        run: echo "Hello world"


```

---

## 📌 Actions utiles

```yaml

- uses: actions/checkout@v3         # cloner le repo
- uses: actions/setup-java@v3       # installer Java
  with:
    java-version: '17'

- run: mvn test                     # lancer les tests
- run: pandoc guide.md -o guide.pdf # générer un PDF

- uses: actions/upload-artifact@v3  # sauvegarder un fichier
  with:
    name: doc
    path: guide.pdf


```

## Exemples pratiques

1. **Workflow simple qui dit bonjour**
```yaml

name: Hello

on: [push]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - run: echo "Hello from GitHub Actions!"

```

2. **Build Spring Boot + tests** 
```yaml
name: Build Java

on: [push]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
      - run: mvn clean install
      - run: mvn test
```

3. **Génerer un PDF depuis Markdown et l'enregistrer 
```yaml
name: Build Doc

on: [push]

jobs:
  doc:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - run: sudo apt-get update && sudo apt-get install -y pandoc texlive-xetex
      - run: pandoc serveur_musique_guide.md -o serveur_musique_guide.pdf
      - uses: actions/upload-artifact@v3
        with:
          name: guide-pdf
          path: serveur_musique_guide.pdf
```