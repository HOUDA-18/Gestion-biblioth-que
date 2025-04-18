import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { loan } from '../models/loan';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  private apiUrl = 'http://localhost:8086/loan'; 
    constructor(private http: HttpClient) { }
  
    // Get all loans
      getloans(): Observable<loan[]> {
        return this.http.get<loan[]>(this.apiUrl);
      }
    
      // Get single loan
      getloan(id: number): Observable<loan> {
        return this.http.get<loan>(`${this.apiUrl}/${id}`);
      }
    
      // Create loan
      createloan(loan: loan): Observable<loan> {
        return this.http.post<loan>(this.apiUrl, loan);
      }
    
      // Update loan
      updateloan(id: number, loan: loan): Observable<loan> {
        return this.http.put<loan>(`${this.apiUrl}/${id}`, loan);
      }
    
      // Delete loan
      deleteloan(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
      }
}
