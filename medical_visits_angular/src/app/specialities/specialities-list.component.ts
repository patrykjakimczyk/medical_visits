
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Speciality } from 'src/app/model/doctor-list-elem';
import { ApiService } from 'src/app/service/api.service';


@Component({
  selector: 'app-specialities-list',
  templateUrl: './specialities-list.component.html',
  styleUrls: ['./specialities-list.component.css']
})

export class SpecialitiesListComponent implements OnInit {
  protected specialities: Speciality[];

  constructor(private apiService: ApiService, private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.apiService.getSpecialities().subscribe({
      next: (specialities: Speciality[]) => {
        this.specialities = specialities;      }
    })
  }

  goToDoctorsBySpeciality(index: number) {
    this.router.navigate([`speciality/${this.specialities[index].id}/doctors`, {specialityName: this.specialities[index].specialityName}], {relativeTo: this.activatedRoute});
  }
}
