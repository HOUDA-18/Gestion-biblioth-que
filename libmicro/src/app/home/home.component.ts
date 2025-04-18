import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(private router: Router) {}
  books = [
    { 
      title: 'The Great Adventure', 
      author: 'Jane Smith', 
      genre: 'Fiction', 
      publisher: 'Book House', 
      available: true 
    },
    { 
      title: 'Science Fundamentals', 
      author: 'John Doe', 
      genre: 'Non-Fiction', 
      publisher: 'Science Press', 
      available: false 
    },
    { 
      title: 'Mystery of the Ages', 
      author: 'Emily Brown', 
      genre: 'Mystery', 
      publisher: 'Puzzle Books', 
      available: true 
    }
  ];

  loans = [
    { 
      loanDate: new Date('2024-03-01'), 
      returnDate: new Date('2024-03-15'), 
      bookTitle: 'History of the World' 
    },
    { 
      loanDate: new Date('2024-02-20'), 
      returnDate: new Date('2024-03-05'), 
      bookTitle: 'Introduction to Programming' 
    }
  ];

  logout() {
    // Add any logout logic here (clear tokens/sessions)
    this.router.navigate(['/login']);
  }
}
