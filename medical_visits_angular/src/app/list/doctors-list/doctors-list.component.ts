  import {Component, OnInit } from '@angular/core';
  import { ApiService } from 'src/app/service/api.service';
  import { HttpErrorResponse } from '@angular/common/http';
  import { DoctorListElem } from 'src/app/model/doctor-list-elem';
  import { Sort, SortProperty, FilterTypeParam, FILTER_TYPES, FilterTypeName } from 'src/app/model/get-method-enums';
  import { NgForm } from '@angular/forms';

  @Component({
    selector: 'app-doctors-list',
    templateUrl: './doctors-list.component.html',
    styleUrls: ['./doctors-list.component.css']
  })
  export class DoctorsListComponent implements OnInit{
    public filterType: {param: FilterTypeParam, name: FilterTypeName}[];
    protected currentPage: number;
    protected totalPages: number;
    protected doctorsArray: DoctorListElem[];

    constructor(private apiService: ApiService) {}

    ngOnInit(): void {
      this.filterType = FILTER_TYPES;
      this.currentPage = 0;
      this.totalPages = 1;
      this.doctorsArray = [];
      this.insertDoctors(this.currentPage);
    }

    insertDoctors(page: number, filterType?: FilterTypeParam, filterKey?: string) {
      if (filterType == undefined && filterKey == undefined) {
        this.apiService.getDoctors(page).subscribe({
          next: (response: any) => {
             this.doctorsArray = response.content;
            console.log(response);
          },
          error: (error: HttpErrorResponse) => {
            console.log(error)
          }
        });
      } else {
        this.apiService.getDoctors(page, filterType, filterKey).subscribe({
          next: (response: any) => {
            this.doctorsArray = [...response.content];
            this.totalPages = response.totalPages;
            console.log(response);
          },
          error: (error: HttpErrorResponse) => {
            console.log(error)
          }
        });
      }
    }

    previousPage() {
      if (this.currentPage > 0) {
        this.currentPage--;
        this.insertDoctors(this.currentPage);
      }
    }

    nextPage() {
      if (this.currentPage < this.totalPages - 1) {
        this.currentPage++;
        this.insertDoctors(this.currentPage);
      }
    }

    onSubmit(form: NgForm) {
      this.currentPage = 0;
      console.log(form);
      this.insertDoctors(this.currentPage, form.form.controls["filterType"].value, form.form.controls["filterKey"].value);
    }
  }
