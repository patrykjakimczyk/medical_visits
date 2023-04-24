import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { User } from '../model/user';

@Injectable({providedIn:"root"})
export class ApiService {
  public static url = "http://localhost:8081/";

  constructor(private http: HttpClient) {}

  registerPatient(form: NgForm): Observable<User> {
    return this.http.post<User>(`${ApiService.url}registerPatient`, form.value)
      .pipe(
        map((user) => {
          return user;
        }),
        catchError((error) => {
          console.error(error);
          throw error;
        })
      );
  }

  login(form: NgForm): Observable<User> {
    return this.http.post<User>(`${ApiService.url}login`, form.value)
      .pipe(
         map(user => {
          return user;
        }),
        catchError((error) => {
          console.error(error);
          throw error as HttpErrorResponse;
        })
      );
  }
}
