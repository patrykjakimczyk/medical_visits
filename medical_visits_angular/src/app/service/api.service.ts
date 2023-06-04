import { HttpClient, HttpErrorResponse, HttpParams, HttpHeaders } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, switchMap } from 'rxjs';

import { User } from '../model/user';
import { AuthenticationService } from './authentication.service';
import { FilterTypeParam, Sort, SortProperty } from '../model/get-method-enums';
import { DoctorListElem, Speciality } from '../model/doctor-list-elem';
import { PatientListElem } from '../model/patient-list-elem';


@Injectable({providedIn:"root"})
export class ApiService {
  public static url = "http://localhost:8082/";

  constructor(private http: HttpClient, private authService: AuthenticationService) {}

  registerPatient(form: NgForm): Observable<User> {
    return this.http.post<User>(`${ApiService.url}registerPatient`, form.value)
      .pipe(
        map((user) => user),
        catchError((error) => {
          console.error(error);
          throw error as HttpErrorResponse;
        })
      );
  }

  login(form: NgForm): Observable<User> {
    return this.http.post<User>(`${ApiService.url}login`, form.value)
      .pipe(
        map(user => user),
        catchError((error) => {
          console.error(error);
          throw error as HttpErrorResponse;
        })
      );
  }

  getSpecialities(): Observable<Speciality[]> {
    return this.http.get<Speciality[]>(`${ApiService.url}getSpecialities`)
    .pipe(
      map(specialities => specialities),
      catchError((error) => {
        console.error(error);
        throw error as HttpErrorResponse;
      })
    );
  }

  getPatients(page: number, sorts: Map<SortProperty, Sort>, filterType?: FilterTypeParam, filterKey?: string): Observable<PatientListElem[]> {
    return this.authService.loggedUser.pipe(
      switchMap((user) => {
        let headers = new HttpHeaders();
        let queryParams = new HttpParams();

        if (user !== undefined && Object.keys(user).length !== 0) {
          headers = headers.set("Authorization", `Bearer ${user.token}`);
        }

        if (filterType != undefined && filterKey != undefined)  {
          queryParams = queryParams
              .append("filterType", filterType)
              .append("filterKey", filterKey);
        }

        const firstNameSortOrder = sorts.get(SortProperty.FIRST_NAME);
        if (firstNameSortOrder != undefined)  {
          queryParams = queryParams
              .append(SortProperty.FIRST_NAME, firstNameSortOrder);
        }

        const lastNameSortOrder = sorts.get(SortProperty.LAST_NAME);
        if (lastNameSortOrder != undefined)  {
          queryParams = queryParams
              .append(SortProperty.LAST_NAME, lastNameSortOrder);
        }

        queryParams = queryParams.append("offset", page.toString());
        queryParams = queryParams.append("pageSize", "10")

        return this.http.get<any>(`${ApiService.url}auth/admin/patients`, {
          headers: headers,
          params: queryParams
        })
      }),
      catchError((error) => {
        console.error(error);
        throw error as HttpErrorResponse;
      })
    );
  }

  getPatientsForDoctor(page: number, sorts: Map<SortProperty, Sort>, filterType?: FilterTypeParam, filterKey?: string): Observable<PatientListElem[]> {
    return this.authService.loggedUser.pipe(
      switchMap((user) => {
        let headers = new HttpHeaders();
        let queryParams = new HttpParams();

        if (user !== undefined && Object.keys(user).length !== 0) {
          headers = headers.set("Authorization", `Bearer ${user.token}`);
        }

        if (filterType != undefined && filterKey != undefined)  {
          queryParams = queryParams
              .append("filterType", filterType)
              .append("filterKey", filterKey);
        }

        const firstNameSortOrder = sorts.get(SortProperty.FIRST_NAME);
        if (firstNameSortOrder != undefined)  {
          queryParams = queryParams
              .append(SortProperty.FIRST_NAME, firstNameSortOrder);
        }

        const lastNameSortOrder = sorts.get(SortProperty.LAST_NAME);
        if (lastNameSortOrder != undefined)  {
          queryParams = queryParams
              .append(SortProperty.LAST_NAME, lastNameSortOrder);
        }

        queryParams = queryParams.append("offset", page.toString());
        queryParams = queryParams.append("pageSize", "10")

        return this.http.get<any>(`${ApiService.url}auth/doctor/doctorsPatients`, {
          headers: headers,
          params: queryParams
        })
      }),
      catchError((error) => {
        console.error(error);
        throw error as HttpErrorResponse;
      })
    );
  }

  getDoctors(page: number, pageSize: number = 10, filterType?: FilterTypeParam, filterKey?: string, sorts?: Map<SortProperty, Sort>): Observable<any> {
    return this.authService.loggedUser.pipe(
      switchMap((user) => {
        let headers = new HttpHeaders();
        let queryParams = new HttpParams();

        if (user && Object.keys(user).length !== 0) {
          headers = headers.set("Authorization", `Bearer ${user.token}`);
        }

        if (filterType && filterKey)  {
          queryParams = queryParams
              .append("filterType", filterType)
              .append("filterKey", filterKey);
        }

        if (sorts)  {
          const firstNameSortOrder = sorts.get(SortProperty.FIRST_NAME);
          const lastNameSortOrder = sorts.get(SortProperty.LAST_NAME);

          if (firstNameSortOrder)  {
            queryParams = queryParams
            .append(SortProperty.FIRST_NAME, firstNameSortOrder);
          }

          if (lastNameSortOrder) {
            queryParams = queryParams
                .append(SortProperty.LAST_NAME, lastNameSortOrder);
          }
        }

        queryParams = queryParams.append("offset", page.toString());
        queryParams = queryParams.append("pageSize", pageSize.toString())

        return this.http.get<any>(`${ApiService.url}auth/admin/doctors`, {
          headers: headers,
          params: queryParams
        })
      }),
      catchError((error) => {
        console.error(error);
        throw error as HttpErrorResponse;
      })
    );
  }

  getPatientsFullData(id: number): Observable<any> {
    return this.authService.loggedUser.pipe(
      switchMap((user) => {
        let headers = new HttpHeaders();
        let queryParams = new HttpParams();

        if (user !== undefined && Object.keys(user).length !== 0) {
          headers = headers.set("Authorization", `Bearer ${user.token}`);
        }

        queryParams = queryParams
              .append("id", id);

        return this.http.get<any>(`${ApiService.url}auth/patient/patientData`, {
          headers: headers,
          params: queryParams
        }).pipe(
          map(patientData => patientData),
          catchError(error => {
            console.error(error);
            throw error as HttpErrorResponse;
          })
        )
      })
    );
  }

  editPatientsData(patient: any): Observable<any> {
    return this.authService.loggedUser.pipe(
      switchMap(user => {
        let headers = new HttpHeaders({
          'Content-Type': 'application/json'
        });

        if (user !== undefined && Object.keys(user).length !== 0) {
          headers = headers.set("Authorization", `Bearer ${user.token}`);
        }

        return this.http.patch<any>(`${ApiService.url}auth/doctor/updatePatient`, patient, {
          headers: headers
        })
          .pipe(
            map(response => response),
            catchError(error => {
                console.error(error);
                throw error as HttpErrorResponse;
            }));
      })
    )
  }

  getDoctorsFullData(id: number): Observable<any> {
    return this.authService.loggedUser.pipe(
      switchMap((user) => {
        let headers = new HttpHeaders();
        let queryParams = new HttpParams();

        if (user !== undefined && Object.keys(user).length !== 0) {
          headers = headers.set("Authorization", `Bearer ${user.token}`);
        }

        queryParams = queryParams
              .append("id", id);

        return this.http.get<any>(`${ApiService.url}auth/doctor/doctorData`, {
          headers: headers,
          params: queryParams
        }).pipe(
          map(doctorData => doctorData),
          catchError(error => {
            console.error(error);
            throw error as HttpErrorResponse;
          })
        )
      })
    );
  }

  editDoctorsData(doctor: DoctorListElem): Observable<any> {
    return this.authService.loggedUser.pipe(
      switchMap(user => {
        let headers = new HttpHeaders({
          'Content-Type': 'application/json'
        });

        if (user !== undefined && Object.keys(user).length !== 0) {
          headers = headers.set("Authorization", `Bearer ${user.token}`);
        }

        return this.http.patch<any>(`${ApiService.url}auth/doctor/updateDoctor`, doctor, {
          headers: headers
        })
          .pipe(
            map(response => response),
            catchError(error => {
                console.error(error);
                throw error as HttpErrorResponse;
            }));
      })
    )
  }
}
