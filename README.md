# Microservice Publisher

Microservice de gestion des éditeurs (Publishers) avec des fonctionnalités avancées de recherche et d'analyse.

## Fonctionnalités

- CRUD complet pour les éditeurs
- Recherche multicritère
- Statistiques démographiques
- Recherche géolocalisée
- Intégration avec des services externes

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database 



## API Endpoints

### Gestion des éditeurs

| Méthode | Endpoint                | Description                          |
|---------|-------------------------|--------------------------------------|
| GET     | `/publishers/all`       | Liste tous les éditeurs              |
| GET     | `/publishers/getPub/{id}`| Récupère un éditeur par ID           |
| POST    | `/publishers`           | Crée un nouvel éditeur               |
| PUT     | `/publishers/update`    | Met à jour un éditeur                |
| DELETE  | `/publishers/delete/{id}`| Supprime un éditeur                  |

### Recherche

| Méthode | Endpoint                | Description                          |
|---------|-------------------------|--------------------------------------|
| GET     | `/publishers/search`    | Recherche par nom ou localisation    |
| GET     | `/publishers/nearby`    | Recherche géolocalisée               |

### Statistiques

| Méthode | Endpoint                | Description                          |
|---------|-------------------------|--------------------------------------|
| GET     | `/publishers/stats-demographic` | Statistiques démographiques    |

