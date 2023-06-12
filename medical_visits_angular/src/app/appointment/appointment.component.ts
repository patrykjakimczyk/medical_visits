import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { DoctorListElem, Speciality } from 'src/app/model/doctor-list-elem';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css', '../forms_css.css']
})

export class AppointmentComponent implements OnInit {
  specialities: Speciality[];
  protected doctors: DoctorListElem[] | null;
  protected assignedDoctor: DoctorListElem;
  protected message: string;
  protected errorMessage: string;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getSpecialities().subscribe((specs => {
      this.specialities = specs;
    }))
  }

  getDoctorsBySpeciality(event: any) {
    const specialityId = event.target.value;

    if (specialityId === "0") {
      this.doctors = null;
      return;
    }

    this.apiService.getDoctorsBySpeciality(0, 1000, specialityId).subscribe({
      next: (doctors: any) => {
        this.doctors = [...doctors.content];
      }
    });
  }

  assignDoctor(doctor: DoctorListElem) {
    this.assignedDoctor = doctor;
    this.doctors = null;
  }

  getDoctorsFullName(): string {
    return this.assignedDoctor && this.assignedDoctor.firstName && this.assignedDoctor.lastName
      ? `${this.assignedDoctor.firstName} ${this.assignedDoctor.lastName}`
      : "";
  }

  onSubmit(form: NgForm) {
    const appointment = form.value;
    const datetime = appointment.date + " " + appointment.time;
    const timestamp = new Date(datetime.replace(" ", "T")).getTime();

    appointment.doctorId = this.assignedDoctor.id;
    appointment.timestamp = timestamp

    delete appointment.doctor;
    delete appointment.time;
    delete appointment.date;

    this.apiService.registerVisit(appointment).subscribe({
      next: _ => {
        this.errorMessage = "";
        this.message = "Visit has been successfully registered"
      },
      error: (_: HttpErrorResponse) => {
        this.errorMessage = "Error occured during visit's registration";
        this.message = "";
      }
    })
  }
}
