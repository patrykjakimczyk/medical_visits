import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-visits-list-for-doctor',
  templateUrl: './visits-list-for-doctor.component.html',
  styleUrls: ['./visits-list-for-doctor.component.css', '../doctors-list/doctors-list.component.css']
})
export class VisitsListForDoctorComponent {
  protected visits: any[];
  protected currentPage: number;
  protected totalPages: number;


  constructor(private apiService: ApiService, private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.currentPage = 0;
    this.getAllVisits();
  }

  getAllVisits() {
    this.apiService.getVisitsForDoctor(this.currentPage).subscribe({
      next: response => {
        this.totalPages = response.totalPages;
        this.visits = response.content;
        this.visits.forEach((element: any) => {
          let timestamp: string = element.timeStamp.toString();
          timestamp = timestamp.substring(0, timestamp.length - 10);
          element.timeStamp = new Date(timestamp);
        });
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
      }
    });
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getAllVisits();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.getAllVisits();
    }
  }

  getDate(date: any) {
   return `${date.getFullYear()}-${date.getMonth() <= 9 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)}-${date.getDate() <= 9 ? "0" + date.getDate() : date.getDate()}, ` +
    `${date.getHours() <= 9 ? "0" + date.getHours() : date.getHours()}:${date.getMinutes() <= 9 ? "0" + date.getMinutes() : date.getMinutes()}`;
  }

  goToDetails(index: number) {
    this.router.navigate([`visit/${this.visits[index].id}/details`], {relativeTo: this.activatedRoute});
  }

  goToEdit(index: number) {
    this.router.navigate([`visit/${this.visits[index].id}/edit`], {relativeTo: this.activatedRoute});
  }
}
