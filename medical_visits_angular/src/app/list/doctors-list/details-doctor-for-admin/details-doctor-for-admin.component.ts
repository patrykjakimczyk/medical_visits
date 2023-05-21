import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-details-doctor-for-admin',
  templateUrl: './details-doctor-for-admin.component.html',
  styleUrls: ['./details-doctor-for-admin.component.css', '../../../forms_css.css']
})
export class DetailsDoctorForAdminComponent implements OnInit{
  protected doctorsData: any;

  constructor(private activatedRoute: ActivatedRoute, private apiService: ApiService) {}

  ngOnInit(): void {
    const doctorsId = this.activatedRoute.snapshot.params["id"];
    this.apiService.getDoctorsFullData(doctorsId).subscribe({
      next: (response: any) => {
        this.doctorsData = response;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    });
  }
}
