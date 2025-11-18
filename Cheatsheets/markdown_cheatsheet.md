# 📝 Cheatsheet Markdown
---
## 🔑 Mots-clés

- `# : titre (# = h1, ## = h2, etc.)`

- `**- ou *** : liste à puces`

- `1. 2. 3. : liste numérotée`

- `code : code inline`

- `bloc : bloc de code`

- `  : lien [texte](url)`

- ` : image ![alt](url)`

- `> : citation`

- `--- : ligne horizontale`

- `|…| : tableau`


---

## 📌 Syntaxe essentielle

```markdown
# Titre H1
## Titre H2
### Titre H3

**gras** et *italique*

- Liste non ordonnée
- Élément 2

1. Liste ordonnée
2. Élément 2

`code inline`

````java
// Bloc de code Java
System.out.println("Hello");
````

| Nom   | Âge |
| ----- | --- |
| Alice | 24  |
| Bob   | 30  |

---

## 🧩 Exemples pratiques
1. **Documentation projet**
```markdown
# Serveur de Musique
Projet Spring Boot pour gérer la lecture et les playlists.

## Fonctionnalités
- Historique des chansons
- Playlists par utilisateur
- Classement des titres

## Commandes

```bash
mvn spring-boot:run

```

2. **README GitHub avec badge**
```markdown
# Mon Projet ![Build](https://github.com/user/repo/actions/workflows/build.yml/badge.svg)
```