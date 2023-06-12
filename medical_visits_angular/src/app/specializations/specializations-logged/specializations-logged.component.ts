
import { Component, OnInit } from '@angular/core';
import { Speciality } from 'src/app/model/doctor-list-elem';
import { ApiService } from 'src/app/service/api.service';


@Component({
  selector: 'app-specializations-logged',
  templateUrl: './specializations-logged.component.html',
  styleUrls: ['./specializations-logged.component.css', '../specializations-unlogged/specializations-unlogged.component.css']
})

export class SpecializationsLoggedComponent implements OnInit{
  protected specialities: Speciality[];

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getSpecialities().subscribe({
      next: (specialities: Speciality[]) => {
        this.specialities = specialities;
      }
    })
  }
}
