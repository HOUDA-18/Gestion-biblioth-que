import { Component } from '@angular/core';
interface User {
  id: number;
  name: string;
  email: string;
  role: string;
}
interface Loan {
  id: number;
  bookTitle: string;
  userName: string;
  loanDate: Date;
  dueDate: Date;
  status: string;
}
interface LibraryCard {
  id: number;
  userName: string;
  cardNumber: string;
  issueDate: Date;
  expiryDate: Date;
  status: string;
}

@Component({
  selector: 'app-admin-loans-users',
  templateUrl: './admin-loans-users.component.html',
  styleUrls: ['./admin-loans-users.component.css']
})
export class AdminLoansUsersComponent {
  users: User[] = [
    { id: 1, name: 'John Doe', email: 'john@library.com', role: 'Patron' },
    { id: 2, name: 'Jane Smith', email: 'jane@library.com', role: 'Librarian' }
  ];

  showUserModal = false;
  selectedUser: User | null = null;
  currentUser: User = this.createEmptyUser();

  createEmptyUser(): User {
    return { id: 0, name: '', email: '', role: 'Patron' };
  }

  openUserModal() {
    this.showUserModal = true;
  }

  closeUserModal() {
    this.showUserModal = false;
    this.selectedUser = null;
    this.currentUser = this.createEmptyUser();
  }

  addUser() {
    this.currentUser.id = this.users.length + 1;
    this.users.push({...this.currentUser});
    this.closeUserModal();
  }

  editUser(user: User) {
    this.selectedUser = user;
    this.currentUser = {...user};
    this.openUserModal();
  }

  updateUser() {
    const index = this.users.findIndex(u => u.id === this.currentUser.id);
    if (index > -1) {
      this.users[index] = {...this.currentUser};
    }
    this.closeUserModal();
  }

  deleteUser(id: number) {
    this.users = this.users.filter(user => user.id !== id);
  }

  loans: Loan[] = [
    { 
      id: 1, 
      bookTitle: 'Sample Book 1', 
      userName: 'John Doe', 
      loanDate: new Date('2024-03-01'), 
      dueDate: new Date('2024-03-15'), 
      status: 'Returned' 
    },
    { 
      id: 2, 
      bookTitle: 'Science Fundamentals', 
      userName: 'Jane Smith', 
      loanDate: new Date(), 
      dueDate: new Date(new Date().setDate(new Date().getDate() + 14)), 
      status: 'Active' 
    }
  ];

  allBooks = [
    { title: 'Sample Book 1' },
    { title: 'Science Fundamentals' }
  ];

  allUsers = [
    { name: 'John Doe' },
    { name: 'Jane Smith' }
  ];

  showLoanModal = false;
  selectedLoan: Loan | null = null;
  currentLoan: Loan = this.createEmptyLoan();

  createEmptyLoan(): Loan {
    return { 
      id: 0, 
      bookTitle: '', 
      userName: '', 
      loanDate: new Date(), 
      dueDate: new Date(), 
      status: 'Active' 
    };
  }

  openLoanModal() {
    this.showLoanModal = true;
  }

  closeLoanModal() {
    this.showLoanModal = false;
    this.selectedLoan = null;
    this.currentLoan = this.createEmptyLoan();
  }

  addLoan() {
    this.currentLoan.id = this.loans.length + 1;
    this.loans.push({...this.currentLoan});
    this.closeLoanModal();
  }

  editLoan(loan: Loan) {
    this.selectedLoan = loan;
    this.currentLoan = {...loan};
    this.openLoanModal();
  }

  updateLoan() {
    const index = this.loans.findIndex(l => l.id === this.currentLoan.id);
    if (index > -1) {
      this.loans[index] = {...this.currentLoan};
    }
    this.closeLoanModal();
  }

  deleteLoan(id: number) {
    this.loans = this.loans.filter(loan => loan.id !== id);
  }
  libraryCards: LibraryCard[] = [
    { 
      id: 1,
      userName: 'John Doe',
      cardNumber: 'LIB-2024-001',
      issueDate: new Date('2024-01-01'),
      expiryDate: new Date('2027-01-01'),
      status: 'Active'
    },
    { 
      id: 2,
      userName: 'Jane Smith',
      cardNumber: 'LIB-2024-002',
      issueDate: new Date('2024-02-01'),
      expiryDate: new Date('2027-02-01'),
      status: 'Active'
    }
  ];

  showCardModal = false;
  selectedCard: LibraryCard | null = null;
  currentCard: LibraryCard = this.createEmptyCard();

  createEmptyCard(): LibraryCard {
    return { 
      id: 0,
      userName: '',
      cardNumber: this.generateCardNumber(),
      issueDate: new Date(),
      expiryDate: new Date(new Date().setFullYear(new Date().getFullYear() + 3)),
      status: 'Active'
    };
  }

  generateCardNumber(): string {
    const prefix = 'LIB-' + new Date().getFullYear() + '-';
    const nextId = this.libraryCards.length + 1;
    return prefix + nextId.toString().padStart(3, '0');
  }

  openCardModal() {
    this.currentCard = this.createEmptyCard();
    this.showCardModal = true;
  }

  closeCardModal() {
    this.showCardModal = false;
    this.selectedCard = null;
  }

  addCard() {
    this.currentCard.id = this.libraryCards.length + 1;
    this.libraryCards.push({...this.currentCard});
    this.closeCardModal();
  }

  editCard(card: LibraryCard) {
    this.selectedCard = card;
    this.currentCard = {...card};
    this.openCardModal();
  }

  updateCard() {
    const index = this.libraryCards.findIndex(c => c.id === this.currentCard.id);
    if (index > -1) {
      this.libraryCards[index] = {...this.currentCard};
    }
    this.closeCardModal();
  }

  deleteCard(id: number) {
    this.libraryCards = this.libraryCards.filter(card => card.id !== id);
  }
}
