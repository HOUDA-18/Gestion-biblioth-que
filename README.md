# Système de Gestion de Bibliothèque

Le **Le Système de Gestion de Bibliothèque**  est une application à la fois simple et puissante, conçue pour gérer des collections de livres. Il offre des fonctionnalités de base telles que l'ajout, la modification, la suppression et la consultation des ouvrages, ainsi que des options avancées, ce qui le rend idéal pour les bibliothèques, les collections personnelles ou les établissements éducatifs.
## API Reference

#### Get all loans

```http
  GET /loan
```
| Parameter   | Type      | Description                                                         |
| :---------- | :-------- | :------------------------------------------------------------------ |
| `loanId`    | `Integer` | **Required**. Identifiant unique du prêt.                           |
| `bookId`    | `Integer` | **Required**. Identifiant du livre concerné par le prêt.             |
| `cardNumber`| `String`  | **Required**. Numéro de carte de l'emprunteur.                       |
| `loanDate`  | `Date`    | **Required**. Date à laquelle le prêt a été initié.                  |
| `returnDate`| `Date`    | **Required**. Date prévue pour le retour du livre.                   |


#### Get loan by id

```http
GET /loan/{loanId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of loan to fetch |

#### Update loan by id

```http
  PUT /loan/{loanId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of loan to fetch |


#### Delete loan by id

```http
 DELETE /loan/{loanId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of loan to fetch |


#### Add a new loan

```http
  POST /loan
```

#### Get loans by filters

```http
  GET  /loan/filter (http://localhost:8086/loan/filter?bookId=4)
```


#### Get loan statistics

```http
 GET /loan/statistics
```


#### Get Adjust return date for holiday

```http
  GET /loan/{loanId}/adjust-return-date(http://localhost:8090/loan/11/adjust-return-date?countryCode=TN)
```


