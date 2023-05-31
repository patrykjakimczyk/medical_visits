  import {Component, OnInit } from '@angular/core';
  import { ApiService } from 'src/app/service/api.service';
  import { HttpErrorResponse } from '@angular/common/http';
  import { DoctorListElem } from 'src/app/model/doctor-list-elem';
  import { Sort, SORT_ORDERS, SortProperty, FilterTypeParam, FILTER_TYPES, FilterTypeName } from 'src/app/model/get-method-enums';
  import { NgForm } from '@angular/forms';
  import { ActivatedRoute, Router } from '@angular/router';

  @Component({
    selector: 'app-doctors-list',
    templateUrl: './doctors-list.component.html',
    styleUrls: ['./doctors-list.component.css']
  })
  export class DoctorsListComponent implements OnInit{
    protected filterTypes: {param: FilterTypeParam, name: FilterTypeName}[];
    protected currentPage: number;
    protected totalPages: number;
    protected doctorsArray: DoctorListElem[];
    protected firstNameSortIndex: number;
    protected lastNameSortIndex: number;
    private _SortOrders: typeof SORT_ORDERS;
    private _filterType?: FilterTypeParam;
    private _filterKey?: string;
    private _currentSorts: Map<SortProperty, Sort>;


    constructor(private apiService: ApiService, private router: Router, private activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
      this.filterTypes = FILTER_TYPES;
      this.currentPage = 0;
      this.totalPages = 1;
      this.doctorsArray = [];
      this.firstNameSortIndex = 0;
      this.lastNameSortIndex = 0;
      this._SortOrders = SORT_ORDERS;
      this._currentSorts = new Map<SortProperty, Sort>();
      this.insertDoctors();
    }

    insertDoctors() {
      if (this._filterType == undefined && this._filterKey == undefined) {
        this.apiService.getDoctors(this.currentPage, 10, undefined, undefined, this._currentSorts).subscribe({
          next: (response: any) => {
             this.doctorsArray = response.content;
             this.totalPages = response.totalPages;
          },
          error: (error: HttpErrorResponse) => {
            console.log(error)
          }
        });
      } else {
        this.apiService.getDoctors(this.currentPage, 10,  this._filterType, this._filterKey, this._currentSorts).subscribe({
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
      console.log(this.firstNameSortIndex, this._SortOrders.length);

      if (this.firstNameSortIndex === 2) {
        this.firstNameSortIndex = 0;
        this._currentSorts.delete(SortProperty.FIRST_NAME);
        this.insertDoctors();
        return;
      }

      this._currentSorts.set(SortProperty.FIRST_NAME, this._SortOrders[this.firstNameSortIndex]);
      this.firstNameSortIndex++;
      this.insertDoctors();
    }

    lastNameSorting() {
      this.currentPage = 0;
      console.log(this.lastNameSortIndex, this._SortOrders.length);

      if (this.lastNameSortIndex === 2) {
        this.lastNameSortIndex = 0;
        this._currentSorts.delete(SortProperty.LAST_NAME);
        this.insertDoctors();
        return;
      }

      this._currentSorts.set(SortProperty.LAST_NAME, this._SortOrders[this.lastNameSortIndex]);
      this.lastNameSortIndex++;
      this.insertDoctors();
    }

    onSubmit(form: NgForm) {
      this.currentPage = 0;
      this._filterType = form.form.controls["filterType"].value;
      this._filterKey = form.form.controls["filterKey"].value;
      this.insertDoctors();
    }

    goToDetails(index: number) {
      this.router.navigate([`doctor/${this.doctorsArray[index].id}/details`], {relativeTo: this.activatedRoute});
    }

    goToEdit(index: number) {
      this.router.navigate([`doctor/${this.doctorsArray[index].id}/edit`], {relativeTo: this.activatedRoute});
    }
  }
