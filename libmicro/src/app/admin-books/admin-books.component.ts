import { Component, OnInit } from '@angular/core';
import { GenreService } from '../services/genre.service';
import { genre } from '../models/genre';
import { publisher } from '../models/publisher';
import { PublisherService } from '../services/publisher.service';
import { author } from '../models/author';
import { AuthorService } from '../services/author.service';
import { bookService } from '../services/book.service';
import { book } from '../models/book';

@Component({
  selector: 'app-admin-books',
  templateUrl: './admin-books.component.html',
  styleUrls: ['./admin-books.component.css']
})

export class AdminBooksComponent implements OnInit {
  ngOnInit(): void {
    this.getAllGenres();
    this.getAllBooks();
    this.getAllAuthors();
    this.getAllPublishers();
    this.loadDependencies();
  }

  constructor(
     private genreService: GenreService
    ,private publisherService: PublisherService
    ,private authorService: AuthorService
    ,private bookService: bookService
     ) { }

     books: book[] = [];
     authors: any[] = [];
     showModal = false;
     selectedBook: book | null = null;
     currentBook: book = this.createEmptyBook();

     newAuthorName = '';
     selectedAuthor: author | null = null;

  // Publishers
  selectedPublisher: publisher | null = null;
  publishers: publisher[] = [];
  newPublisherName = '';
  newPublisherAge: number | null = null;
  newPublisherLocation = '';
  newPublisher = '';

  // Genres
  genres: genre[] = [];
  newGenreName = '';
  newGenreDescription = '';
  newGenre = '';
  editingGenre: genre | null = null;
  selectedGenre: genre | null = null;
  

  createEmptyBook(): book {
    return {
      bookId: 0,
      title: '',
      status: 'AVAILABLE',
      numberAvailableCopies: 0,
      authorId: 0,
      publisherId: 0,
      idGenre: 0
    };
  }

  getAllBooks(): void {
    this.bookService.getbooks().subscribe({
      next: (books) => this.books = books,
      error: (err) => console.error('Error loading books:', err)
    });
  }

  loadDependencies(): void {
    this.authorService.getauthors().subscribe(authors => this.authors = authors);
    this.publisherService.getpublishers().subscribe(publishers => this.publishers = publishers);
    this.genreService.getgenres().subscribe(genres => this.genres = genres);
  }

  addBook(): void {
    this.bookService.createbook(this.currentBook).subscribe({
      next: (createdBook) => {
        this.books.push(createdBook);
        this.closeModal();
      },
      error: (err) => console.error('Error adding book:', err)
    });
  }

  editBook(book: book): void {
    this.selectedBook = book;
    this.currentBook = { ...book };
    this.openModal();
  }

  updateBook(): void {
    if (this.currentBook.bookId) {
      this.bookService.updatebook(this.currentBook.bookId, this.currentBook).subscribe({
        next: (updatedBook) => {
          const index = this.books.findIndex(b => b.bookId === updatedBook.bookId);
          if (index > -1) {
            this.books[index] = updatedBook;
          }
          this.closeModal();
        },
        error: (err) => console.error('Error updating book:', err)
      });
    }
  }

  deleteBook(bookId: number): void {
    this.bookService.deletebook(bookId).subscribe({
      next: () => {
        this.books = this.books.filter(book => book.bookId !== bookId);
      },
      error: (err) => console.error('Error deleting book:', err)
    });
  }

  openModal(): void {
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedBook = null;
    this.currentBook = this.createEmptyBook();
  }

  // Get all authors
  getAllAuthors(): void {
    this.authorService.getauthors().subscribe({
      next: (authors) => this.authors = authors,
      error: (err) => console.error('Error fetching authors:', err)
    });
  }

  // Add new author
  addAuthor(): void {
    if (this.newAuthorName.trim()) {
      const newAuthor: author = {
        id: 0, // Let backend generate ID
        name: this.newAuthorName.trim(),
        books: []
      };

      this.authorService.createauthor(newAuthor).subscribe({
        next: (createdAuthor) => {
          this.authors.push(createdAuthor);
          this.newAuthorName = '';
        },
        error: (err) => console.error('Error adding author:', err)
      });
    }
  }

  // Update author
  updateAuthor(): void {
    if (this.selectedAuthor) {
      this.authorService.updateauthor(this.selectedAuthor.id, this.selectedAuthor)
        .subscribe({
          next: (updatedAuthor) => {
            const index = this.authors.findIndex(a => a.id === updatedAuthor.id);
            if (index > -1) {
              this.authors[index] = updatedAuthor;
            }
            this.selectedAuthor = null;
          },
          error: (err) => console.error('Error updating author:', err)
        });
    }
  }

  // Delete author
  deleteAuthor(id: number): void {
    this.authorService.deleteauthor(id).subscribe({
      next: () => {
        this.authors = this.authors.filter(a => a.id !== id);
      },
      error: (err) => console.error('Error deleting author:', err)
    });
  }

  // Edit author
  editAuthor(author: author): void {
    this.selectedAuthor = { ...author };
  }

  // Get all publishers
  getAllPublishers(): void {
    this.publisherService.getpublishers().subscribe({
      next: (publishers) => this.publishers = publishers,
      error: (err) => console.error('Error fetching publishers:', err)
    });
  }

  // Add new publisher
  addPublisher(): void {
    if (this.newPublisherName.trim() && this.newPublisherAge) {
      const newPublisher: publisher = {
        publisherId: 0, // Let backend generate ID
        name: this.newPublisherName.trim(),
        age: this.newPublisherAge,
        localisation: this.newPublisherLocation.trim()
      };

      this.publisherService.createpublisher(newPublisher).subscribe({
        next: (createdPublisher) => {
          this.publishers.push(createdPublisher);
          this.clearPublisherForm();
        },
        error: (err) => console.error('Error adding publisher:', err)
      });
    }
  }

  // Update publisher
  updatePublisher(pub: publisher): void {
    this.publisherService.updatepublisher(pub.publisherId, pub).subscribe({
      next: (updatedPublisher) => {
        const index = this.publishers.findIndex(p => p.publisherId === updatedPublisher.publisherId);
        if (index > -1) {
          this.publishers[index] = updatedPublisher;
        }
      },
      error: (err) => console.error('Error updating publisher:', err)
    });
  }

  // Delete publisher
  deletePublisher(publisherId: number): void {
    this.publisherService.deletepublisher(publisherId).subscribe({
      next: () => {
        this.publishers = this.publishers.filter(p => p.publisherId !== publisherId);
      },
      error: (err) => console.error('Error deleting publisher:', err)
    });
  }

  private clearPublisherForm(): void {
    this.newPublisherName = '';
    this.newPublisherAge = null;
    this.newPublisherLocation = '';
  }

    editPublisher(publisher: publisher): void {
      this.selectedPublisher = { ...publisher };
    }
    

    // Get all genres
  getAllGenres(): void {
    this.genreService.getgenres().subscribe({
      next: (genres) => this.genres = genres,
      error: (err) => console.error('Error fetching genres:', err)
    });
  }

  // Add new genre
  addGenre(): void {
    if (this.newGenreName.trim()) {
      const newGenre: genre = {
        idGenre: 0, // Will be generated by backend
        name: this.newGenreName.trim(),
        description: this.newGenreDescription.trim(),
        popularity: 0 // Default popularity
      };

      this.genreService.creategenre(newGenre).subscribe({
        next: (createdGenre) => {
          this.genres.push(createdGenre);
          this.newGenreName = '';
          this.newGenreDescription = '';
        },
        error: (err) => console.error('Error adding genre:', err)
      });
    }
  }

  // Update genre
  updateGenre(genre: genre): void {
    this.genreService.updateGenre(genre).subscribe({
      next: (updatedGenre) => {
        const index = this.genres.findIndex(g => g.idGenre === updatedGenre.idGenre);
        if (index > -1) {
          this.genres[index] = updatedGenre;
        }
        this.selectedGenre = null;
      },
      error: (err) => console.error('Error updating genre:', err)
    });
  }
  logAndDelete(genre: any) {
    console.log("Clicked genre:", genre);
    this.deleteGenre(genre.idGenre); // or genre.id
  }
  // Delete genre
  deleteGenre(idGenre: number): void {
    console.log("ID à supprimer :", idGenre);
    this.genreService.deletegenre(idGenre).subscribe({
      next: () => {
        this.genres = this.genres.filter(g => g.idGenre !== idGenre);
      },
      error: (err) => console.error('Error deleting genre:', err)
    });
  }

  editGenre(genre: genre): void {
    this.selectedGenre = { ...genre };
  }

  saveEdit(): void {
    if (this.selectedGenre) {
      this.updateGenre(this.selectedGenre);
      this.selectedGenre = null;
    }
  }
}
