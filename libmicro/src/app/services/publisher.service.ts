import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { publisher } from '../models/publisher';

@Injectable({
  providedIn: 'root'
})
export class PublisherService {

  private apiUrl = 'http://localhost:8086/publisher'; 
    constructor(private http: HttpClient) { }
  
    // Get all publishers
      getpublishers(): Observable<publisher[]> {
        return this.http.get<publisher[]>(this.apiUrl);
      }
    
      // Get single publisher
      getpublisher(id: number): Observable<publisher> {
        return this.http.get<publisher>(`${this.apiUrl}/${id}`);
      }
    
      // Create publisher
      createpublisher(publisher: publisher): Observable<publisher> {
        return this.http.post<publisher>(this.apiUrl, publisher);
      }
    
      // Update publisher
      updatepublisher(id: number, publisher: publisher): Observable<publisher> {
        return this.http.put<publisher>(`${this.apiUrl}/${id}`, publisher);
      }
    
      // Delete publisher
      deletepublisher(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
      }
}
