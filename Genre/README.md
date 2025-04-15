# Library Management System

The **Library Management System** is a simple yet powerful application that allows users to manage a collection of books. It provides functionalities to add, update, delete, and view books and some advanced functionalities making it ideal for libraries, personal collections, or educational institutions.

## API Reference

#### Get all Genres

```http
  GET /Genre/
```

| Parameter          | Type       | Description                        |
| :------------------| :-------   | :----------------------------------|
| `name`             | `string`   | **Required**. name of genre        |
| `description`      | `string`   | **Required**. description of genre |
| `popularity`       |  `integer` | **Required**. popularity of genre  |

#### Get genre by id

```http
  GET /Genre/${id}
```

| Parameter | Type     | Description                        |
| :-------- | :------- | :--------------------------------  |
| `id`      | `string` | **Required**. Id of genre to fetch |

#### Update genre by id

```http
  PUT /Genre/
```

| Parameter | Type       | Description                       |
| :-------- | :-------   | :-------------------------------- |
| `Genre`   | `genre`    | **Required**. updated genre       |


#### Delete book by id

```http
  DELETE /Genre/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of genre to fetch |


#### Add a new book

```http
  POST /Genre/
```

#### Get Top 5 genres

```http
  GET /Genre/Top5
```

#### update Popularity of genre 

```http
  PUT /Genre/updatePop
```
