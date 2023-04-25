import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { RegistationFormComponent } from './form/registation-form/registation-form.component';
import { LoginFormComponent } from './form/login-form/login-form.component';
import { HomePageLoggedComponent } from './home-page-logged/home-page-logged.component';
import { PatientsListComponent } from './list/patients-list/patients-list.component';
import { DoctorsListComponent } from './list/doctors-list/doctors-list.component';


const routes: Routes = [
  {
    path: '', component: HomePageComponent,
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
    path: 'patients-list',
    component: PatientsListComponent
  }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
