  import {Component, OnInit } from '@angular/core';
  import { ApiService } from 'src/app/service/api.service';
  import { HttpErrorResponse } from '@angular/common/http';
  import { DoctorListElem } from 'src/app/model/doctor-list-elem';
  import { Sort, SORT_ORDERS, SortType, SortProperty, FilterTypeParam, FILTER_TYPES, FilterTypeName } from 'src/app/model/get-method-enums';
  import { NgForm } from '@angular/forms';

  @Component({
    selector: 'app-doctors-list',
    templateUrl: './doctors-list.component.html',
    styleUrls: ['./doctors-list.component.css']
  })
  export class DoctorsListComponent implements OnInit{
    public filterTypes: {param: FilterTypeParam, name: FilterTypeName}[];
    private filterType?: FilterTypeParam;
    private filterKey?: string;
    protected currentPage: number;
    protected totalPages: number;
    protected doctorsArray: DoctorListElem[];
    private SortOrders: typeof SORT_ORDERS;
    private firstNameSortIndex: number;
    private lastNameSortIndex: number;
    private currentSorts: Map<SortProperty, Sort>;


    constructor(private apiService: ApiService) {}

    ngOnInit(): void {
      this.filterTypes = FILTER_TYPES;
      this.currentPage = 0;
      this.totalPages = 1;
      this.doctorsArray = [];
      this.firstNameSortIndex = 0;
      this.lastNameSortIndex = 0;
      this.SortOrders = SORT_ORDERS;
      this.currentSorts = new Map<SortProperty, Sort>();
      this.insertDoctors();
    }

    insertDoctors() {
      if (this.filterType == undefined && this.filterKey == undefined) {
        this.apiService.getDoctors(this.currentPage, this.currentSorts).subscribe({
          next: (response: any) => {
             this.doctorsArray = response.content;
             this.doctorsArray
          },
          error: (error: HttpErrorResponse) => {
            console.log(error)
          }
        });
      } else {
        this.apiService.getDoctors(this.currentPage, this.currentSorts, this.filterType, this.filterKey).subscribe({
          next: (response: any) => {
            this.doctorsArray = [...response.content];
            this.totalPages = response.totalPages;
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
        this.insertDoctors();
      }
    }

    nextPage() {
      if (this.currentPage < this.totalPages - 1) {
        this.currentPage++;
        this.insertDoctors();
      }
    }

    firstNameSorting() {
      this.currentPage = 0;
      console.log(this.firstNameSortIndex, this.SortOrders.length);

      if (this.firstNameSortIndex === 2) {
        this.firstNameSortIndex = 0;
        this.currentSorts.delete(SortProperty.FIRST_NAME);
        this.insertDoctors();
        return;
      }

      this.currentSorts.set(SortProperty.FIRST_NAME, this.SortOrders[this.firstNameSortIndex]);
      this.firstNameSortIndex++;
      this.insertDoctors();
    }

    lastNameSorting() {
      this.currentPage = 0;
      console.log(this.lastNameSortIndex, this.SortOrders.length);

      if (this.lastNameSortIndex === 2) {
        this.lastNameSortIndex = 0;
        this.currentSorts.delete(SortProperty.LAST_NAME);
        this.insertDoctors();
        return;
      }

      this.currentSorts.set(SortProperty.LAST_NAME, this.SortOrders[this.lastNameSortIndex]);
      this.lastNameSortIndex++;
      this.insertDoctors();
    }

    onSubmit(form: NgForm) {
      this.currentPage = 0;
      this.filterType = form.form.controls["filterType"].value;
      this.filterKey = form.form.controls["filterKey"].value;
      this.insertDoctors();
    }
  }
