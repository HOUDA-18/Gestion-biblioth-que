import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { AdminBooksComponent } from './admin-books/admin-books.component';
import { AdminLoansUsersComponent } from './admin-loans-users/admin-loans-users.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent }, 
  { path: 'admin/books', component: AdminBooksComponent },// Replace with your home component
  { path: 'admin/loans-users', component: AdminLoansUsersComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
