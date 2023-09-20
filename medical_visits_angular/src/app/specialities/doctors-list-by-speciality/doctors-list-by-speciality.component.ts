import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DoctorListElem, Speciality } from 'src/app/model/doctor-list-elem';
import { FILTER_TYPES_WO_PESEL, FilterTypeName, FilterTypeParam, SORT_ORDERS, Sort, SortProperty } from 'src/app/model/get-method-enums';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-doctors-list-by-speciality',
  templateUrl: './doctors-list-by-speciality.component.html',
  styleUrls: ['./doctors-list-by-speciality.component.css']
})
export class DoctorsListBySpecialityComponent implements OnInit{
  protected speciality: Speciality;
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

  constructor(private activatedRoute: ActivatedRoute, private apiService: ApiService) {

  }

  ngOnInit(): void {
    this.speciality = {
      id: this.activatedRoute.snapshot.params["id"],
      specialityName: this.activatedRoute.snapshot.paramMap.get('specialityName') as string
    }
    this.filterTypes = FILTER_TYPES_WO_PESEL;
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
    console
    if (this._filterType == undefined && this._filterKey == undefined) {
      this.apiService.getDoctorsBySpeciality(this.currentPage, 10, this.speciality.id, undefined, undefined, this._currentSorts).subscribe({
        next: (response: any) => {
          this.doctorsArray = [...response.doctors.content];
          this.totalPages = response.doctors.totalPages;
        },
        error: (error: HttpErrorResponse) => {
          console.log(error)
        }
      });
    } else {
      this.apiService.getDoctorsBySpeciality(this.currentPage, 10,  this.speciality.id, this._filterType, this._filterKey, this._currentSorts).subscribe({
        next: (response: any) => {
          this.doctorsArray = [...response.doctors.content];
          this.totalPages = response.doctors.totalPages;
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
}
