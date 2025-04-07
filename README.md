# Microservice Author
Microservice de gestion des auteurs (Authors) avec système complet de CRUD et pagination.

## Fonctionnalités
- CRUD complet pour les auteurs
- Système de pagination avancé
- Recherche multicritère
- Filtrage par genre littéraire

## Technologies
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL Database

## API Endpoints

### Gestion des auteurs
| Méthode | Endpoint               | Description                     |
|---------|------------------------|---------------------------------|
| GET     | `/author/all-list`         | Liste tous les auteurs          |
| GET     | `/author/{id}`    | Récupère un auteur par ID       |
| POST    | `/author`             | Crée un nouvel auteur           |
| PUT     | `/author/update/{id}`      | Met à jour un auteur            |
| DELETE  | `/author/delete/{id}` | Supprime un auteur              |

### Recherche et pagination
| Méthode | Endpoint               | Description                     |
|---------|------------------------|---------------------------------|
| GET     | `/author/search`      | Recherche paginée (nom/email)   |
| GET     | `/author/all`    | Filtre paginé par genre         |

### Paramètres de pagination
| Paramètre  | Description                          | Valeur par défaut |
|------------|--------------------------------------|------------------|
| `page`     | Numéro de page (0-based)             | 0                |
| `size`     | Nombre d'éléments par page           | 10               |
| `sort`     | Champ de tri (ex: name,asc)          | id               |
