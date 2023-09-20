import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DoctorListElem } from 'src/app/model/doctor-list-elem';
import { FILTER_TYPES, FilterTypeName, FilterTypeParam } from 'src/app/model/get-method-enums';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-edit-patient-for-doctor',
  templateUrl: './edit-patient-for-doctor.component.html',
  styleUrls: ['./edit-patient-for-doctor.component.css', '../../../forms_css.css']
})
export class EditPatientForDoctorComponent {
    protected patientData: any;
  protected doctors: DoctorListElem[] | null;
  protected filterTypes: {param: FilterTypeParam, name: FilterTypeName}[];
  protected filterType: FilterTypeParam;
  protected message: string;
  protected errorMessage: string;

  constructor(private activatedRoute: ActivatedRoute, private apiService: ApiService) {}

  ngOnInit(): void {
    this.filterTypes = FILTER_TYPES;
    const doctorsId = this.activatedRoute.snapshot.params["id"];
    this.apiService.getPatientsFullData(doctorsId).subscribe({
      next: (response: any) => {
        this.patientData = response;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    });
  }

  getDoctorsFullName(): string {
    return this.patientData.assignedDoctor && this.patientData.assignedDoctor.firstName && this.patientData.assignedDoctor.lastName
      ? `${this.patientData.assignedDoctor.firstName} ${this.patientData.assignedDoctor.lastName}`
      : "";
  }

  onSubmit(form: NgForm) {
    const patient = form.value;
    delete patient.doctorName;
    delete patient.assignedDoctor;
    patient.assignedDoctorId = this.patientData.assignedDoctor.id;
    patient.id = this.patientData.id;

    this.apiService.editPatientsData(patient).subscribe({
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
}
