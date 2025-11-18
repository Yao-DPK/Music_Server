# Cheatsheet Bash (scripts)

---

## 🔑 Mots-clés

- **#!/bin/bash**: shebang → indique que le script doit être exécuté avec bash.
- **chmod +x script.sh**: rend un script exécutable.
- **$variable**: accéder à une variable.
- **$1, $2…**: arguments passés au script.
- **if … then … fi**: condition.
- **for … do … done**: boucle.
- **echo**: afficher un texte.
- **exit**: terminer un script


---
## 📌 Commandes essentielles
```bash
#!/bin/bash

# Variables
name="Pyke"
echo "Hello $name"

# Lire une entrée
read user_input
echo "Vous avez écrit: $user_input"

# Condition
if [ $user_input == "ok" ]; then
  echo "Parfait"
else
  echo "Pas ok"
fi

# Boucle
for i in 1 2 3; do
  echo "Tour $i"
done

# Arguments
echo "Le premier argument est $1"
```
---
## 🧩 Exemples pratiques

1. Script pour lancer un projet
```bash 
#!/bin/bash
echo "Nom du fichier: $1"
cp $1 /tmp/
```

2. Script avec arguments
```bash
#!/bin/bash
echo "Nom du fichier: $1"
cp $1 /tmp/
```
Utilisation: 
```bash
./backup.sh monfichier.txt
```

3. Script pour lancer le back et  le front d'un projet (sans Docker)
```bash
#!/bin/bash

gnome-terminal -- bash -c "cd backend && mvn spring-boot:run; exec bash"
sleep 10
gnome-terminal -- bash -c "cd frontend && npm start; exec bash"
```
Excellent — question fondamentale pour automatiser proprement tes scripts.
Je vais être **brutalement clair et complet**, sans bullshit : il existe **3 façons principales** de récupérer le résultat d’une commande Bash, selon **ce que tu veux récupérer** (valeur de retour, sortie texte, ou sortie structurée).

---

## 🧩 1️⃣ Récupérer la **sortie (stdout)** d’une commande

C’est la méthode la plus courante.
Tu veux capturer ce que la commande **affiche dans le terminal**.

### ➤ Syntaxe

```bash
result=$(commande)
```

ou équivalent moderne :

```bash
result=$(commande)
```

(ne jamais utiliser les backticks obsolètes `commande`)

### ➤ Exemple

```bash
current_branch=$(git branch --show-current)
echo "Je suis sur la branche: $current_branch"
```

### ➤ Exemple avec plusieurs mots

```bash
files=$(ls /home/pyke/scripts)
echo "Les fichiers sont: $files"
```

⚠️ Ici tout est sur une seule ligne, séparé par des espaces.
Si tu veux **une liste réelle** :

```bash
readarray -t files <<< "$(ls /home/pyke/scripts)"
for f in "${files[@]}"; do
  echo "$f"
done
```

---

## 🧩 2️⃣ Récupérer le **code de retour (exit code)** d’une commande

Ce n’est **pas** ce qui est affiché, mais **si la commande a réussi ou échoué**.

### ➤ Syntaxe

```bash
commande
code_retour=$?
echo "Code retour: $code_retour"
```

### ➤ Exemple

```bash
ping -c 1 8.8.8.8 > /dev/null
if [ $? -eq 0 ]; then
  echo "Connexion Internet OK"
else
  echo "Pas de connexion"
fi
```

🎯 **Usage typique** : détection d’erreur, condition dans un script, vérification avant exécution d’une autre commande.

---

## 🧩 3️⃣ Combiner les deux (stdout + code retour)

Souvent tu veux **la sortie + savoir si la commande a réussi**.

### ➤ Exemple

```bash
result=$(curl -s https://api.github.com)
status=$?

if [ $status -eq 0 ]; then
  echo "Requête réussie"
  echo "$result"
else
  echo "Erreur (code $status)"
fi
```

---

## 🧩 4️⃣ Récupérer des **valeurs structurées / parsées**

Quand ta commande renvoie du JSON, XML, CSV, etc.

### ➤ Exemple avec `jq` pour du JSON

```bash
response=$(curl -s https://api.github.com/users/octocat)
login=$(echo "$response" | jq -r '.login')
echo "Utilisateur GitHub : $login"
```

### ➤ Exemple avec `awk` pour du texte simple

```bash
cpu_load=$(top -bn1 | grep "Cpu(s)" | awk '{print $2 + $4}')
echo "Charge CPU: $cpu_load%"
```

---

## 🧩 5️⃣ Cas particuliers utiles

### 🔸 Exécuter une commande dans une autre

```bash
echo "Il y a $(ls | wc -l) fichiers dans le dossier courant"
```

### 🔸 Supprimer les retours à la ligne

```bash
result=$(echo -n $(hostname))
```

### 🔸 Ignorer la sortie d’erreur (stderr)

```bash
output=$(commande 2>/dev/null)
```

### 🔸 Capturer **stdout et stderr**

```bash
output=$(commande 2>&1)
```

---

## 🧩 6️⃣ Exemple complet concret

Script Bash :

```bash
#!/bin/bash

# Récupère la date actuelle
current_date=$(date +"%Y-%m-%d %H:%M:%S")

# Teste la connexion réseau
ping -c 1 google.com > /dev/null 2>&1
status=$?

if [ $status -eq 0 ]; then
  echo "[$current_date] ✅ Connexion Internet OK"
else
  echo "[$current_date] ❌ Aucune connexion"
fi
```

---

## 🧠 Résumé critique

| Objectif        | Syntaxe         | Type de valeur  | Exemple                               |                 |
| --------------- | --------------- | --------------- | ------------------------------------- | --------------- |
| Sortie texte    | `result=$(cmd)` | Chaîne          | `branch=$(git branch --show-current)` |                 |
| Code de retour  | `$?`            | Entier          | `if [ $? -eq 0 ]; then ...`           |                 |
| JSON parsé      | `jq`            | Structuré       | `name=$(echo "$res"                   | jq -r '.name')` |
| Sortie + erreur | `$(cmd 2>&1)`   | Chaîne complète | `output=$(ls /fake 2>&1)`             |                 |

---