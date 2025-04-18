import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { author } from '../models/author';
@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private apiUrl = 'http://localhost:8086/author'; 
  constructor(private http: HttpClient) { }

  // Get all authors
  getauthors(): Observable<author[]> {
    return this.http.get<author[]>(this.apiUrl);
  }

  // Get single author
  getauthor(id: number): Observable<author> {
    return this.http.get<author>(`${this.apiUrl}/${id}`);
  }

  // Create author
  createauthor(author: author): Observable<author> {
    return this.http.post<author>(this.apiUrl, author);
  }

  // Update author
  updateauthor(id: number, author: author): Observable<author> {
    return this.http.put<author>(`${this.apiUrl}/${id}`, author);
  }

  // Delete author
  deleteauthor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
