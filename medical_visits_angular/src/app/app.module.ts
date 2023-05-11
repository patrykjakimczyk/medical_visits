import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { RegistationFormComponent } from './form/registation-form/registation-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginFormComponent } from './form/login-form/login-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { PatientsListComponent } from './list/patients-list/patients-list.component';
import { DoctorsListComponent } from './list/doctors-list/doctors-list.component';
import { AppRoutingModule } from './app-routing.module';
import { NavMenuComponent } from './nav-menu/nav-menu.component';
import { MedicalVisitsHeadLogoComponent } from './medical-visits-head-logo/medical-visits-head-logo.component';
import { TopButtonsUnloggedComponent } from './top-buttons/top-buttons-unlogged/top-buttons-unlogged.component';
import { TopButtonsLoggedComponent } from './top-buttons/top-buttons-logged/top-buttons-logged.component';
import { EditPatientForAdminComponent } from './edit/edit-patient-for-admin/edit-patient-for-admin.component';
import { EditPatientForDoctorComponent } from './edit/edit-patient-for-doctor/edit-patient-for-doctor.component';
import { EditDoctorForAdminComponent } from './edit/edit-doctor-for-admin/edit-doctor-for-admin.component';
import { DetailsDoctorForAdminComponent } from './details/details-doctor-for-admin/details-doctor-for-admin.component';
import { DetailsPatientForAdminComponent } from './details/details-patient-for-admin/details-patient-for-admin.component';
import { DetailsPatientForDoctorComponent } from './details/details-patient-for-doctor/details-patient-for-doctor.component';


@NgModule({
  declarations: [
    AppComponent,
    RegistationFormComponent,
    LoginFormComponent,
    HomePageComponent,
    PatientsListComponent,
    DoctorsListComponent,
    NavMenuComponent,
    MedicalVisitsHeadLogoComponent,
    TopButtonsUnloggedComponent,
    TopButtonsLoggedComponent,
    EditPatientForAdminComponent,
    EditPatientForDoctorComponent,
    EditDoctorForAdminComponent,
    DetailsDoctorForAdminComponent,
    DetailsPatientForAdminComponent,
    DetailsPatientForDoctorComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
