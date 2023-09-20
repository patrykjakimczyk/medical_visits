import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-details-patient-for-doctor',
  templateUrl: './details-patient-for-doctor.component.html',
  styleUrls: ['./details-patient-for-doctor.component.css', '../../../forms_css.css']
})
export class DetailsPatientForDoctorComponent {
  protected patientData: any;

  constructor(private activatedRoute: ActivatedRoute, private apiService: ApiService) {}

  ngOnInit(): void {
    const patientsId = this.activatedRoute.snapshot.params["id"];
    this.apiService.getPatientsFullData(patientsId).subscribe({
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
      : ""
  }
}
