import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { book } from '../models/book';

@Injectable({
  providedIn: 'root'
})
export class bookService {

  private apiUrl = 'http://localhost:8086/book'; 
  constructor(private http: HttpClient) { }

  // Get all books
  getbooks(): Observable<book[]> {
    return this.http.get<book[]>(this.apiUrl);
  }

  // Get single book
  getbook(id: number): Observable<book> {
    return this.http.get<book>(`${this.apiUrl}/${id}`);
  }

  // Create book
  createbook(book: book): Observable<book> {
    return this.http.post<book>(this.apiUrl, book);
  }

  // Update book
  updatebook(id: number, book: book): Observable<book> {
    return this.http.put<book>(`${this.apiUrl}/${id}`, book);
  }

  // Delete book
  deletebook(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
