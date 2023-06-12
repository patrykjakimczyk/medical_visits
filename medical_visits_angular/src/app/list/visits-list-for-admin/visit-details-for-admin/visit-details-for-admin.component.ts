import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-visit-details-for-admin',
  templateUrl: './visit-details-for-admin.component.html',
  styleUrls: ['./visit-details-for-admin.component.css', '../../../forms_css.css']
})
export class VisitDetailsForAdminComponent implements OnInit{
  protected visit: any;
  constructor(private route: ActivatedRoute, private apiService: ApiService) {}

  ngOnInit(): void {
    const doctorsId = this.route.snapshot.params["id"];
    this.apiService.getVisitData(doctorsId).subscribe({
      next: visit => {
        this.visit = visit;
        this.visit.timeStamp = new Date(this.visit.timeStamp);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    })
  }

  getDate(date: any) {
    return `${date.getFullYear()}-${date.getMonth() <= 9 ? "0" + date.getMonth() : date.getMonth()}-${date.getDate() <= 9 ? "0" + date.getDate() : date.getDate()}`
   }

   getTime(date: any) {
    return `${date.getHours() <= 9 ? "0" + date.getHours() : date.getHours()}:${date.getMinutes() <= 9 ? "0" + date.getMinutes() : date.getMinutes()}:${date.getSeconds() <= 9 ? "0" + date.getSeconds() : date.getSeconds()}`
   }

}
