import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { ApiService } from 'src/app/service/api.service';
import { NgForm } from '@angular/forms';
import { Speciality } from 'src/app/model/doctor-list-elem';

@Component({
  selector: 'app-edit-doctor-for-admin',
  templateUrl: './edit-doctor-for-admin.component.html',
  styleUrls: ['./edit-doctor-for-admin.component.css', '../../../forms_css.css']
})
export class EditDoctorForAdminComponent implements OnInit{
  private doctorsId: number;
  protected doctorsData: any;
  protected doctorsSpecialities: Set<any>;
  protected allSpecialities: any;
  protected message: string;
  protected errorMessage: string;

  constructor(private activatedRoute: ActivatedRoute, private apiService: ApiService) {}

  ngOnInit(): void {
    this.doctorsId = this.activatedRoute.snapshot.params["id"];
    this.doctorsSpecialities = new Set();
    this.apiService.getDoctorsFullData(this.doctorsId).subscribe({
      next: (response: any) => {
        this.doctorsData = response;

        this.apiService.getSpecialities().subscribe({
          next: (response: Speciality[]) => {
            this.allSpecialities = response;
            for (let speciality of response) {
              for(let doctorsSpeciality of this.doctorsData.specialities) {
                if (speciality.specialityName === doctorsSpeciality.specialityName) {
                  this.doctorsSpecialities.add(speciality);
                  break;
                }
              }
            }
          },
          error: (error: HttpErrorResponse) => {
            console.log(error)
          }
        });
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    });
  }

  onSubmit(form: NgForm) {
    let doctor = form.value;
    Object.assign(doctor, { id: this.doctorsId});
    Object.assign(doctor, {specialities: [...this.doctorsSpecialities].map(speciality => speciality.id)})
    delete doctor.filterType;
    this.apiService.editDoctorsData(doctor).subscribe({
      next: (response: any) => {
        this.message = response.message;
        this.errorMessage = "";
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = error.message;
        this.message = "";
      }
    })
  }

  addSpeciality(speciality: any) {
    this.doctorsSpecialities.add(speciality)
  }

  deleteSpeciality(speciality: any) {
    this.doctorsSpecialities.delete(speciality);
  }
}
