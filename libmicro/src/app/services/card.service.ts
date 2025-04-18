import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { card } from '../models/card';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  private apiUrl = 'http://localhost:8086/card'; 
  constructor(private http: HttpClient) { }

  // Get all cards
    getcards(): Observable<card[]> {
      return this.http.get<card[]>(this.apiUrl);
    }
  
    // Get single card
    getcard(id: number): Observable<card> {
      return this.http.get<card>(`${this.apiUrl}/${id}`);
    }
  
    // Create card
    createcard(card: card): Observable<card> {
      return this.http.post<card>(this.apiUrl, card);
    }
  
    // Update card
    updatecard(id: number, card: card): Observable<card> {
      return this.http.put<card>(`${this.apiUrl}/${id}`, card);
    }
  
    // Delete card
    deletecard(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
