import { HttpClient } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({providedIn:"root"})
export class ApiService {
  private url = "localhost:8081/";

  constructor(private http: HttpClient) {}

  // registerPatient() {
  //   console.log(this.url);
  // }

  registerPatient(form: NgForm): Observable<any> {
    return this.http.post(this.url + "registerPatient", form.value);
  }
}
