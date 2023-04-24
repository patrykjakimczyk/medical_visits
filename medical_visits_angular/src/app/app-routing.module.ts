import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { RegistationFormComponent } from './registation-form/registation-form.component';
import { LoginFormComponent } from './login-form/login-form.component';
<<<<<<< Updated upstream
import { HomePageLoggedComponent } from './home-page-logged/home-page-logged.component';
import { PatientsListComponent } from './patients-list/patients-list.component';
import { DoctorsListComponent } from './doctors-list/doctors-list.component';
=======

>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
  },
  {
    path: 'doctors-list',
    component: DoctorsListComponent,
  },
  {
    path: 'patients-list',
    component: PatientsListComponent
=======
>>>>>>> Stashed changes
  }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
