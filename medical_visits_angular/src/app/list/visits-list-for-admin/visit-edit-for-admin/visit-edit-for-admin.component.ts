import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-visit-edit-for-admin',
  templateUrl: './visit-edit-for-admin.component.html',
  styleUrls: ['./visit-edit-for-admin.component.css', '../../../forms_css.css']
})
export class VisitEditForAdminComponent {
  protected visit: any;
  protected message: string;
  protected errorMessage: string;

  constructor(private route: ActivatedRoute, private apiService: ApiService) {}

  ngOnInit(): void {
    const doctorsId = this.route.snapshot.params["id"];
    this.apiService.getVisitData(doctorsId).subscribe({
      next: visit => {
        this.visit = visit;
        this.visit.timeStamp = new Date(this.visit.timeStamp);
        this.visit.date = this.getDate(this.visit.timeStamp)
        this.visit.time = this.getTime(this.visit.timeStamp)
        console.log(new Date(this.visit.timeStamp).getMonth())
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    })
  }

  getDate(date: any) {
    return `${date.getFullYear()}-${date.getMonth() <= 9 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)}-${date.getDate() <= 9 ? "0" + date.getDate() : date.getDate()}`
   }

   getTime(date: any) {
    return `${date.getHours() <= 9 ? "0" + date.getHours() : date.getHours()}:${date.getMinutes() <= 9 ? "0" + date.getMinutes() : date.getMinutes()}:${date.getSeconds() <= 9 ? "0" + date.getSeconds() : date.getSeconds()}`
   }

   onSubmit(form: NgForm) {
    const appointment = form.value;
    const datetime = appointment.date + " " + appointment.time;
    const timestamp = new Date(datetime.replace(" ", "T")).getTime();


    appointment.timeStamp = timestamp
    appointment.id = this.visit.id;
    appointment.doctorId = this.visit.doctor.id;
    appointment.patientId = this.visit.patient.id;

    delete appointment.doctor;
    delete appointment.time;
    delete appointment.date;

    this.apiService.updateVisit(appointment).subscribe({
      next: _ => {
        this.errorMessage = "";
        this.message = "Visit has been successfully edited"
      },
      error: (_: HttpErrorResponse) => {
        this.errorMessage = "Error occured during visit's edit";
        this.message = "";
      }
    })
   }
}
