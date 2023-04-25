import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { RegistationFormComponent } from './form/registation-form/registation-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginFormComponent } from './form/login-form/login-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { HomePageLoggedComponent } from './home-page-logged/home-page-logged.component';
import { PatientsListComponent } from './list/patients-list/patients-list.component';
import { DoctorsListComponent } from './list/doctors-list/doctors-list.component';
import { AppRoutingModule } from './app-routing.module';
import { NavMenuComponent } from './nav-menu/nav-menu.component';
import { MedicalVisitsHeadLogoComponent } from './medical-visits-head-logo/medical-visits-head-logo.component';
import { TopButtonsUnloggedComponent } from './top-buttons/top-buttons-unlogged/top-buttons-unlogged.component';
import { TopButtonsLoggedComponent } from './top-buttons/top-buttons-logged/top-buttons-logged.component';


@NgModule({
  declarations: [
    AppComponent,
    RegistationFormComponent,
    LoginFormComponent,
    HomePageComponent,
    HomePageLoggedComponent,
    PatientsListComponent,
    DoctorsListComponent,
    NavMenuComponent,
    MedicalVisitsHeadLogoComponent,
    TopButtonsUnloggedComponent,
    TopButtonsLoggedComponent
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
