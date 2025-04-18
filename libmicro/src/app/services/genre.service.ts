import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { genre } from '../models/genre';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  private apiUrl = 'http://localhost:8086/Genre'; 
    constructor(private http: HttpClient) { }
  
    // Get all genres
      getgenres(): Observable<genre[]> {
        return this.http.get<genre[]>(this.apiUrl+'/');
      }
    
      // Get single genre
      getgenre(id: number): Observable<genre> {
        return this.http.get<genre>(`${this.apiUrl}/${id}`);
      }
    
      // Create genre
      creategenre(genre: genre): Observable<genre> {
        return this.http.post<genre>(this.apiUrl+'/', genre);
      }
    
      updateGenre(genre: genre): Observable<genre> {
        return this.http.put<genre>(this.apiUrl+'/', genre);
      }
    
      // Delete genre
      deletegenre(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
      }
}
