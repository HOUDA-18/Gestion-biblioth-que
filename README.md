# Library Management System

The **Library Management System** is a simple yet powerful application that allows users to manage a collection of books. It provides functionalities to add, update, delete, and view books and some advanced functionalities making it ideal for libraries, personal collections, or educational institutions.

## API Reference

#### Get all books

```http
  GET /books
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Not Required**. title of book to fetch |
| `author`      | `integer` | **Not Required**. authorId of book to fetch |
| `publisher`      | `integer` | **Not Required**. publisherId of book to fetch |
| `genre`      | `integer` | **Not Required**. genreId of book to fetch |

#### Get book by id

```http
  GET /books/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of BOOK to fetch |

#### Update book by id

```http
  PUT /books/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of BOOK to fetch |


#### Delete book by id

```http
  DELETE /books/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of BOOK to fetch |


#### Add a new book

```http
  POST /books
```

#### Get books By authors

```http
  GET /books/getBooksByAuthors
```


#### Get books cout by  publisher

```http
  GET /books/getBooksCountByPublisher
```


#### Get books distribution by  status

```http
  GET /books/getBooksCountByStatus
```


