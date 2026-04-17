# Plateforme E-Commerce - Étude Architecturale

Ce projet implémente un système de vente en ligne complet à travers deux paradigmes architecturaux distincts pour en comparer les performances et la maintenabilité : une approche **Monolithe** et une approche **Microservices**.

---

## Architecture Microservices 

La solution microservices repose sur un écosystème d'applications Spring Boot autonomes, chacune spécialisée dans un domaine métier précis et possédant sa propre base de données H2 isolée.

### Services de l'écosystème

1.  **Catalogue Service (Port 8081)** : Gère le référentiel des 10 produits technologiques et l'inventaire en temps réel.
    * **Entité** : `Product` (id, name, price, stock).
2.  **Customer Service (Port 8082)** : Gère les profils utilisateurs et leurs segments de fidélité.
    * **Entité** : `Customer` (id, firstName, lastName, email, status).
3.  **Order Service (Port 8083)** : Service pivot orchestrant les flux d'achat et la communication inter-services.

### Concepts Techniques & Design Patterns

#### Design Pattern Strategy (Calcul des prix)
Le service Commande utilise le **Pattern Strategy** pour appliquer dynamiquement des logiques de réduction sans impacter le code métier principal :
* **StudentDiscountStrategy** : Applique une réduction automatique de **20%** pour les profils étudiants.
* **ClassicDiscountStrategy** : Applique le tarif standard pour les profils classiques.

#### Communication Inter-Services (Orchestration)
Le service `Order-Service` fait office de chef d'orchestre via `RestTemplate` :
* **Agrégation de données** : La route `/full` récupère et assemble les informations du Catalogue et du Customer pour générer un objet `OrderDetails` complet.
* **Mise à jour d'état** : Lors d'un achat (`POST`), le service Commande ordonne au Catalogue de décrémenter le stock via une requête `PUT` synchronisée.

### Endpoints API Principaux

| Service | Méthode | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **Catalogue** | `GET` | `/api/products` | Liste complète des produits et stocks |
| **Customer** | `GET` | `/api/customers/{id}` | Détails du profil et statut de fidélité |
| **Order** | `GET` | `/api/orders/full` | Historique des commandes avec calcul Strategy |
| **Order** | `POST` | `/api/orders` | Création de commande et baisse de stock |

**Exemple de Body pour `POST /api/orders` :**
```json
{
  "customerId": 1,
  "productId": 2,
  "quantity": 1
}

### Déploiement avec Docker (Lancement Facile)

L'ensemble des microservices est conteneurisé avec Docker, ce qui permet de lancer le projet en une seule commande sans avoir à installer Java ou Maven localement.

**La commande à exécuter :**
Ouvrez un terminal à la racine du dossier `microservices/` et exécutez :
```bash
docker compose up --build