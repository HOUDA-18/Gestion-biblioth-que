import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLoansUsersComponent } from './admin-loans-users.component';

describe('AdminLoansUsersComponent', () => {
  let component: AdminLoansUsersComponent;
  let fixture: ComponentFixture<AdminLoansUsersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminLoansUsersComponent]
    });
    fixture = TestBed.createComponent(AdminLoansUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
