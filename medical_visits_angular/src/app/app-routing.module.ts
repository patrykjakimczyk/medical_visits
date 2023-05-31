import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { RegistationFormComponent } from './form/registation-form/registation-form.component';
import { LoginFormComponent } from './form/login-form/login-form.component';
import { PatientsListComponent } from './list/patients-list/patients-list.component';
import { DoctorsListComponent } from './list/doctors-list/doctors-list.component';
import { EditDoctorForAdminComponent } from './list/doctors-list/edit-doctor-for-admin/edit-doctor-for-admin.component';
import { DetailsDoctorForAdminComponent } from './list/doctors-list/details-doctor-for-admin/details-doctor-for-admin.component';
import { EditPatientForAdminComponent } from './list/patients-list/edit-patient-for-admin/edit-patient-for-admin.component';
import { DetailsPatientForAdminComponent } from './list/patients-list/details-patient-for-admin/details-patient-for-admin.component';


const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
  },
  {
    path: 'registration-form',
    component: RegistationFormComponent,
  },
  {
    path: 'login-form',
    component: LoginFormComponent,
  },
  {
    path: 'doctors-list',
    component: DoctorsListComponent,
  },
  {
    path: 'doctors-list',
    children: [
      {
        path: "doctor/:id/edit",
        component: EditDoctorForAdminComponent,
      },
      {
        path: "doctor/:id/details",
        component: DetailsDoctorForAdminComponent,
      }
    ]
  },
  {
    path: 'patients-list',
    component: PatientsListComponent
  },
  {
    path: 'patients-list',
    children: [
      {
        path: "patient/:id/edit",
        component: EditPatientForAdminComponent,
      },
      {
        path: "patient/:id/details",
        component: DetailsPatientForAdminComponent,
      }
    ]
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
