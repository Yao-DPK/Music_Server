# Github CheatSheet 
---
## Mots Clés

- **repo(repository)** : ton projet stockée sur Github
-  **commit** : un snapshot de mon code
-  **push**: envoyer tes commits locaux vers Github
-  **pull**: récupérer les dernieres modifs de Github.
-  **branch**: une ligne de développement parallèle(ec: main, feature/login).
-  **merge**: fusionner une branche dans une autre.
-  **.gitignore**: fichier qui liste l'ensemble des fichiers qie Git ne doit pas suivre
  
---

## Commandes Essentielles

```bash
git config --global user.name "TonNom"        # configurer ton nom
git config --global user.email "ton@mail.com" # configurer ton mail

git init                      # initialiser un repo local
git clone URL                 # cloner un repo GitHub

git status                    # voir les fichiers modifiés
git add fichier               # ajouter un fichier pour commit
git commit -m "message"       # enregistrer un snapshot
git push origin main          # envoyer sur GitHub
git push -u origin main       # -u = crée un tracking entre ta branche locale et la branche distante → plus tard tu pourras juste taper git push ou git pull sans répéter l’URL ni le nom de la branche.
git pull origin main          # récupérer depuis GitHub

git branch                    # lister les branches
git branch -M main            # Renomme la branche actuelle en main
git remote add origin https://github.com/Yao-DPK/Music_Server.git '''
👉 Lie ton dépôt local à un remote repository (ici ton repo GitHub).
origin = nom donné au remote (par convention).
L’URL est ton repo GitHub.
'''


git checkout -b nouvelle_branche  # créer et basculer sur une branche
git merge autre_branche       # fusionner une branche dans la branche courante

git log --oneline             # voir l’historique des commits
```
---

## 🧩 Exemples pratiques

```bash
git config --global user.name "TonNom"        # configurer ton nom
git config --global user.email "ton@mail.com" # configurer ton mail

git init                      # initialiser un repo local
git clone URL                 # cloner un repo GitHub

git status                    # voir les fichiers modifiés
git add fichier               # ajouter un fichier pour commit
git commit -m "message"       # enregistrer un snapshot
git push origin main          # envoyer sur GitHub
git pull origin main          # récupérer depuis GitHub

git branch                    # lister les branches
git checkout -b nouvelle_branche  # créer et basculer sur une branche
git merge autre_branche       # fusionner une branche dans la branche courante

git log --oneline             # voir l’historique des commits
```

