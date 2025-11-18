# 🔹 Étape à ajouter à la Phase 6 — Déploiement avancé

## Phase 6 : Déploiement

1. **Conteneurisation avec Docker**

   * Créer l’image du backend Spring Boot.
   * Créer un docker-compose pour orchestrer backend + PostgreSQL localement.

2. **Déploiement simple (MVP)**

   * Héberger sur un service cloud PaaS (Heroku, Render, Railway).
   * Base de données managée ou locale.

3. **Déploiement avancé (Portfolio – AWS + Kubernetes)**

   * **AWS ECR (Elastic Container Registry)** : stocker les images Docker.
   * **AWS RDS PostgreSQL** : base de données managée.
   * **Kubernetes (AWS EKS ou Minikube local)** : orchestrer les conteneurs.

     * `Deployment` pour ton app Spring Boot.
     * `Service` pour exposer ton API.
     * `ConfigMap` + `Secret` pour gérer la config et credentials DB.
     * `Ingress` pour exposer ton app via HTTP (et éventuellement HTTPS avec Cert-Manager).
   * **CI/CD avec GitHub Actions** :

     * Étape 1 : build et push image vers ECR.
     * Étape 2 : déploiement automatique sur le cluster EKS.

👉 Cette étape bonus te permet de montrer que tu sais :

* travailler en local avec Docker,
* déployer simplement en PaaS (MVP rapide),
* déployer **scalable** en Kubernetes sur AWS (niveau industrie).

---

# 📝 Cheatsheet Kubernetes (Markdown)

---

## 🔑 Concepts clés

* **Pod** : plus petite unité déployable → contient un ou plusieurs conteneurs.
* **Deployment** : gère un ensemble de pods (réplication, mises à jour).
* **Service** : expose un ensemble de pods (ClusterIP, NodePort, LoadBalancer).
* **ConfigMap** : stocke la configuration non sensible (ex: URL DB).
* **Secret** : stocke les infos sensibles (ex: password DB).
* **Ingress** : expose des services via HTTP/HTTPS avec un domaine.
* **Namespace** : cloisonne les ressources dans un cluster.

---

## 📌 Commandes essentielles

### Cluster

```bash
kubectl get nodes             # lister les nœuds
kubectl get pods              # lister les pods
kubectl get services          # lister les services
kubectl get deployments       # lister les déploiements
kubectl describe pod <name>   # détails sur un pod
kubectl logs <pod_name>       # logs d’un pod
kubectl exec -it <pod_name> -- sh   # entrer dans un pod
```

### Appliquer des fichiers YAML

```bash
kubectl apply -f deployment.yaml
kubectl delete -f deployment.yaml
```

---

## 🛠️ YAML types de base

### Deployment (Spring Boot)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: musique-api
spec:
  replicas: 2
  selector:
    matchLabels:
      app: musique-api
  template:
    metadata:
      labels:
        app: musique-api
    spec:
      containers:
      - name: musique-api
        image: <AWS_ECR_REPO>/musique-api:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            configMapKeyRef:
              name: musique-config
              key: db-url
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: musique-secret
              key: db-password
```

---

### Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: musique-service
spec:
  selector:
    app: musique-api
  ports:
    - port: 80
      targetPort: 8080
  type: LoadBalancer
```

---

### ConfigMap

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: musique-config
data:
  db-url: jdbc:postgresql://db:5432/musique
```

---

### Secret

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: musique-secret
type: Opaque
data:
  db-password: c2VjcmV0   # "secret" encodé en base64
```

---

### Ingress

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: musique-ingress
spec:
  rules:
  - host: musique.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: musique-service
            port:
              number: 80
```

---

## 🧩 Exemple pratique — Déploiement Spring Boot

1. Construire et push image vers AWS ECR.
2. `kubectl apply -f configmap.yaml -f secret.yaml -f deployment.yaml -f service.yaml -f ingress.yaml`
3. Vérifier avec :

```bash
kubectl get pods
kubectl get services
kubectl get ingress
```

---
