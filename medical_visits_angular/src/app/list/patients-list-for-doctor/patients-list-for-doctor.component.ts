import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FILTER_TYPES, FilterTypeName, FilterTypeParam, SORT_ORDERS, Sort, SortProperty } from 'src/app/model/get-method-enums';
import { PatientListElem } from 'src/app/model/patient-list-elem';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-patients-list-for-doctor',
  templateUrl: './patients-list-for-doctor.component.html',
  styleUrls: ['./patients-list-for-doctor.component.css']
})
export class PatientsListForDoctorComponent implements OnInit{
  protected filterTypes: {param: FilterTypeParam, name: FilterTypeName}[];
  protected currentPage: number;
  protected totalPages: number;
  protected patientsArray: PatientListElem[]
  protected firstNameSortIndex: number;
  protected lastNameSortIndex: number;
  private _SortOrders: typeof SORT_ORDERS;
  private _filterType?: FilterTypeParam;
  private _filterKey?: string;
  private _currentSorts: Map<SortProperty, Sort>;

  constructor(private apiService: ApiService, private router: Router , private activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
      this.filterTypes = FILTER_TYPES;
      this.currentPage = 0;
      this.totalPages = 1;
      this.patientsArray = [];
      this.firstNameSortIndex = 0;
      this.lastNameSortIndex = 0;
      this._SortOrders = SORT_ORDERS;
      this._currentSorts = new Map<SortProperty, Sort>();
      this.insertPatients();
    }

    insertPatients() {
      if (this._filterType == undefined && this._filterKey == undefined) {
        this.apiService.getPatientsForDoctor(this.currentPage, this._currentSorts).subscribe({
          next: (response: any) => {
             this.patientsArray = response.content;
             this.totalPages = response.totalPages;
          },
          error: (error: HttpErrorResponse) => {
            console.log(error)
          }
        });
      } else {
        this.apiService.getPatientsForDoctor(this.currentPage, this._currentSorts, this._filterType, this._filterKey).subscribe({
          next: (response: any) => {
            this.patientsArray = [...response.content];
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
        this.insertPatients();
      }
    }

    nextPage() {
      if (this.currentPage < this.totalPages - 1) {
        this.currentPage++;
        this.insertPatients();
      }
    }

    firstNameSorting() {
      this.currentPage = 0;
      console.log(this.firstNameSortIndex, this._SortOrders.length);

      if (this.firstNameSortIndex === 2) {
        this.firstNameSortIndex = 0;
        this._currentSorts.delete(SortProperty.FIRST_NAME);
        this.insertPatients();
        return;
      }

      this._currentSorts.set(SortProperty.FIRST_NAME, this._SortOrders[this.firstNameSortIndex]);
      this.firstNameSortIndex++;
      this.insertPatients();
    }

    lastNameSorting() {
      this.currentPage = 0;
      console.log(this.lastNameSortIndex, this._SortOrders.length);

      if (this.lastNameSortIndex === 2) {
        this.lastNameSortIndex = 0;
        this._currentSorts.delete(SortProperty.LAST_NAME);
        this.insertPatients();
        return;
      }

      this._currentSorts.set(SortProperty.LAST_NAME, this._SortOrders[this.lastNameSortIndex]);
      this.lastNameSortIndex++;
      this.insertPatients();
    }

    onSubmit(form: NgForm) {
      this.currentPage = 0;
      this._filterType = form.form.controls["filterType"].value;
      this._filterKey = form.form.controls["filterKey"].value;
      this.insertPatients();
    }

    goToDetails(index: number) {
      this.router.navigate([`patient/${this.patientsArray[index].id}/details`], {relativeTo: this.activatedRoute});
    }

    goToEdit(index: number) {
      this.router.navigate([`patient/${this.patientsArray[index].id}/edit`], {relativeTo: this.activatedRoute});
    }
}
