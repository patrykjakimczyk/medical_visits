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
import { AuthGuard } from './guard/auth-guard';
import { PatientsListForDoctorComponent } from './list/patients-list-for-doctor/patients-list-for-doctor.component';
// import { EditPatientForDoctorComponent } from './list/patients-list-for-doctor/edit-patients-list-for-doctor/edit-patients-list-for-doctor.component';
// import { DetailsPatientForDoctorComponent } from './list/patients-list-for-doctor/details-patients-list-for-doctor/details-patients-list-for-doctor.component';
import { EditPatientForDoctorComponent } from './list/doctors-list/edit-patient-for-doctor/edit-patient-for-doctor.component';
import { DetailsPatientForDoctorComponent } from './list/doctors-list/details-patient-for-doctor/details-patient-for-doctor.component';
import { AppointmentComponent } from './appointment/appointment.component';
import { ContactPageComponent } from './contact-page/contact-page.component';
import { VisitsListForAdminComponent } from './list/visits-list-for-admin/visits-list-for-admin.component';
import { VisitDetailsForAdminComponent } from './list/visits-list-for-admin/visit-details-for-admin/visit-details-for-admin.component';
import { VisitsListForDoctorComponent } from './list/visits-list-for-doctor/visits-list-for-doctor.component';
import { VisitDetailsForDoctorComponent } from './list/visits-list-for-doctor/visit-details-for-doctor/visit-details-for-doctor.component';
import { VisitEditForDoctorComponent } from './list/visits-list-for-doctor/visit-edit-for-doctor/visit-edit-for-doctor.component';
import { VisitEditForAdminComponent } from './list/visits-list-for-admin/visit-edit-for-admin/visit-edit-for-admin.component';
import { SpecializationsLoggedComponent } from './specializations/specializations-logged/specializations-logged.component';


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
    path: 'contact',
    component: ContactPageComponent
  },
  {
    path: 'specialities',
    component: SpecializationsLoggedComponent
  },
  {
    path: 'admin/doctors-list',
    component: DoctorsListComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'ADMIN'
    }
  },
  {
    path: 'admin/doctors-list',
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'ADMIN'
    },
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
    path: 'admin/patients-list',
    component: PatientsListComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'ADMIN'
    }
  },
  {
    path: 'admin/patients-list',
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'ADMIN'
    },
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
    path: 'admin/visits',
    component: VisitsListForAdminComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'ADMIN'
    },
  },
  {
    path: 'admin/visits',
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'ADMIN'
    },
    children: [
      {
        path: "visit/:id/details",
        component: VisitDetailsForAdminComponent
      },
      {
        path: "visit/:id/edit",
        component: VisitEditForAdminComponent
      }
    ]
  },
  {
    path: 'doctor/patients-list',
    component: PatientsListForDoctorComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'DOCTOR'
    }
  },
  {
    path: 'doctor/patients-list',
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'DOCTOR'
    },
    children: [
      {
        path: "patient/:id/edit",
        component: EditPatientForDoctorComponent,
      },
      {
        path: "patient/:id/details",
        component: DetailsPatientForDoctorComponent,
      }
    ]
  },
  {
    path: 'doctor/visits',
    component: VisitsListForDoctorComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'DOCTOR'
    },
  },
  {
    path: 'doctor/visits',
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'DOCTOR'
    },
    children: [
      {
        path: "visit/:id/details",
        component: VisitDetailsForDoctorComponent
      },
      {
        path: "visit/:id/edit",
        component: VisitEditForDoctorComponent
      }
    ]
  },
  {
    path: "patient/register-visit",
    component: AppointmentComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRole: 'PATIENT'
    },
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }
