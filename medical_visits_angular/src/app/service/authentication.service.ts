import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private loggedUserSubject: BehaviorSubject<User | undefined>;
  public loggedUser: Observable<User | undefined>;

  constructor() {
    this.loggedUserSubject = new BehaviorSubject<User | undefined>(JSON.parse(localStorage.getItem('loggedUser') || "{}"));
    this.loggedUser = this.loggedUserSubject.asObservable();
  }

  loginUser(user: User) {
    localStorage.setItem('loggedUser', JSON.stringify(user));
    this.loggedUserSubject.next(user);
  }

  logout() {
    localStorage.removeItem('loggedUser');
    this.loggedUserSubject.next(undefined);
  }
}
