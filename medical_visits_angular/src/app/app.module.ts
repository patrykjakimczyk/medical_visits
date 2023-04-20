import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { RegistationFormComponent } from './registation-form/registation-form.component';
import { ApiService } from './service/api.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginFormComponent } from './login-form/login-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { RouterModule, Routes } from '@angular/router';
import { HomePageLoggedComponent } from './home-page-logged/home-page-logged.component';
import { PatientsListComponent } from './patients-list/patients-list.component';
import { DoctorsListComponent } from './doctors-list/doctors-list.component';

const appRoutes: Routes = [
  {
    path: 'registration-form',
    component: RegistationFormComponent,
  },
  {
    path: 'login-form',
    component: LoginFormComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    RegistationFormComponent,
    LoginFormComponent,
    HomePageComponent,
    HomePageLoggedComponent,
    PatientsListComponent,
    DoctorsListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }
    )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
