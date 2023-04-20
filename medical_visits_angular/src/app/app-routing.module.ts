import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { RegistationFormComponent } from './registation-form/registation-form.component';


const routes: Routes = [
  {
    path: '', component: HomePageComponent,
  },
  {
    path: 'registration-form',
    component: RegistationFormComponent,
  }
]


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
