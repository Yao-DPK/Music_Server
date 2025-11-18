# 📝 Cheatsheet Structures de données – Java

---

## 1. Array (Tableau)

- **Caractéristiques**: taille fixe, accès index en O(1), insertions/suppressions coûteuses.
- **Quand l'utiliser?**: si tu connais la taille à l'avance et que tu veux un accès rapide.

### Exemple 

```java
int[] numbers = {1, 2, 3, 4};
System.out.println(numbers[2]); // 3
```

## 2. ArrayList

Caractéristiques : tableau dynamique, accès index en O(1), insertions/suppressions en milieu O(n).

Quand l’utiliser : liste dynamique avec accès rapide en lecture.

### Exemple

```java
import java.util.ArrayList;

ArrayList<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("C");
System.out.println(list.get(1)); // B
```

## 3. LinkedList

Caractéristiques : liste chaînée double, insertions/suppressions rapides en début/fin, accès index O(n).

Quand l’utiliser : quand tu fais beaucoup d’ajouts/suppressions.

### Exemple
```java
import java.util.LinkedList;

LinkedList<String> linked = new LinkedList<>();
linked.add("X");
linked.add("Y");
linked.addFirst("Start");
System.out.println(linked); // [Start, X, Y]

```
## 4. Stack (Pile)

Caractéristiques : LIFO (Last In, First Out).

Quand l’utiliser : annulation (undo), navigation (back/forward).

### Exemple
```java
import java.util.Stack;

Stack<String> stack = new Stack<>();
stack.push("A");
stack.push("B");
System.out.println(stack.pop()); // B
```

## 5. Queue (File)

Caractéristiques : FIFO (First In, First Out).

Quand l’utiliser : système d’attente, traitement par ordre d’arrivée.

### Exemple
```java
import java.util.LinkedList;
import java.util.Queue;

Queue<String> queue = new LinkedList<>();
queue.add("1");
queue.add("2");
System.out.println(queue.poll()); // 1
```

## 6. Deque (Double-ended Queue)

Caractéristiques : insertion/suppression aux deux extrémités.

Quand l’utiliser : structure flexible (pile + file).

### Exemple
```java
import java.util.ArrayDeque;
import java.util.Deque;

Deque<String> deque = new ArrayDeque<>();
deque.addFirst("First");
deque.addLast("Last");
System.out.println(deque.removeFirst()); // First
```

## 7. HashSet

Caractéristiques : pas de doublons, pas d’ordre garanti, O(1) pour ajout/recherche.

Quand l’utiliser : stocker des valeurs uniques rapidement.

### Exemple
```java
import java.util.HashSet;

HashSet<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");
System.out.println(set); // [A, B]
```

## 8. TreeSet

Caractéristiques : ensemble trié (Red-Black Tree), O(log n) pour opérations.

Quand l’utiliser : quand l’ordre naturel ou trié est important.

### Exemple
```java
import java.util.TreeSet;

TreeSet<Integer> treeSet = new TreeSet<>();
treeSet.add(5);
treeSet.add(1);
treeSet.add(3);
System.out.println(treeSet); // [1, 3, 5]
```

## 9. HashMap

Caractéristiques : clé → valeur, O(1) pour ajout/recherche, pas d’ordre garanti.

Quand l’utiliser : accès rapide par clé.

### Exemple
```java
import java.util.HashMap;

HashMap<String, Integer> map = new HashMap<>();
map.put("Alice", 25);
map.put("Bob", 30);
System.out.println(map.get("Bob")); // 30
```

## 10. TreeMap

Caractéristiques : clé → valeur triées, O(log n) pour opérations.

Quand l’utiliser : besoin de tri des clés.

### Exemple

```java
import java.util.TreeMap;

TreeMap<String, Integer> treeMap = new TreeMap<>();
treeMap.put("Z", 3);
treeMap.put("A", 1);
System.out.println(treeMap); // {A=1, Z=3}
```

## 11. PriorityQueue (File de priorité / Heap)

Caractéristiques : extrait toujours le plus petit (min-heap par défaut).

Quand l’utiliser : traitement par priorité.

### Exemple
```java
import java.util.PriorityQueue;

PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(10);
pq.add(1);
pq.add(5);
System.out.println(pq.poll()); // 1
```

## 12. Trie (Arbre préfixe, implémentation manuelle)

Caractéristiques : recherche par préfixe, utile pour l’autocomplétion.

Quand l’utiliser : dictionnaires, suggestions.

### Exemple (simplifié)
```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord;
}

class Trie {
    TrieNode root = new TrieNode();

    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null)
                node.children[idx] = new TrieNode();
            node = node.children[idx];
        }
        node.isEndOfWord = true;
    }
}
```

## [Feature] — Décision de structure de données

### 0) Description rapide
- Besoin métier : …

### 1) Profil d’opérations
| Opération | Fréquence | Critique (Oui/Non) |
|-----------|-----------|--------------------|
| Lookup par clé | …/s | … |
| Insertion fin | …/s | … |
| Suppression début | …/s | … |
| Itération complète | …/min | … |
| Range query (A..B) | … | … |

### 2) Invariants & contraintes
- Ordre : (aucun / insertion / trié)
- Unicité : (oui/non)
- Comparabilité : (compareTo/comparator ?)
- Capacité : (fixe / extensible)

### 3) Échelle & objectifs
- Taille attendue : …
- Latence cible (p95) : …
- Complexités requises : (ex. lookup ~ O(1), insert ~ O(1))
- Budget mémoire : (faible/moyen/élevé)

### 4) Mutabilité & accès
- Ratio lectures/écritures : …
- Accès (random / séquentiel / batch / streaming) : …
- Range queries nécessaires : (oui/non)

### 5) Concurrence
- Multi-threads : (oui/non)
- Thread-safe requis : (oui/non)
- Stratégie : (structures concurrentes / verrous / confinement)

### 6) Persistance & cache
- In-memory only / avec DB ?
- Indices DB utiles ? (B-Tree, unique, composite)
- Cache côté app ? (TTL, taille max)

### 7) Candidats & comparaison
| Candidat | Opérations fortes | Points faibles | Mémoire | Contraintes (ordre/tri) |
|---------|--------------------|---------------|---------|-------------------------|
| HashMap<K,V> | Lookup O(1) | Pas d’ordre | moyen | ordre: non |
| LinkedHashMap<K,V> | Ordre insertion + O(1) | mémoire > HashMap | moyen+ | ordre: insertion |
| TreeMap<K,V> | Trié, range O(log n) | O(log n) | moyen | ordre: tri |
| ArrayDeque<T> | Extrémités O(1) | Pas d’accès aléatoire | faible | ordre: insertion |

*(Adapte cette table à ton cas.)*

### 8) Choix
- Structure(s) retenue(s) : …
- Justification : …

### 9) Politique de capacité / backpressure
- Taille max : …
- Politique : (éviction FIFO / refus / autre)

### 10) Encapsulation
- Interface publique : (méthodes offertes)
- Détails cachés : (structure interne remplaçable)

### 11) Tests & mesure
- Invariants testés : …
- Cas limites : …
- Test perf (jeu de données ~ …) : …
- Concurrence (si applicable) : …

## ⚡ Template Light – Choix de structure de données
### 1. Fonctionnalité

👉 Décris en une phrase le besoin (ex. historique des chansons).

### 2. Opérations dominantes

👉 Ajout / suppression / recherche / tri / parcours ? (note le plus fréquent).

### 3. Contraintes

👉 Ordre (aucun / insertion / trié)
👉 Unicité (oui/non)
👉 Taille (fixe/dynamique)

### 4. Échelle & perf

👉 Petit / moyen / grand volume ?
👉 Accès rapide nécessaire (O(1)) ou OK pour O(log n) ?

### 5. Choix + justification rapide

👉 Structure retenue (HashMap, Queue, etc.)
👉 1 phrase de justification.

### 🧩 Exemple d’application (MVP musique)
Feature : Historique des chansons
Opérations dominantes : ajout + suppression du dernier
Contraintes : ordre LIFO
Échelle : moyen
Choix : Stack → parfait pour LIFO, simple et efficace.
