import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-registation-form',
  templateUrl: './registation-form.component.html',
  styleUrls: ['./registation-form.component.css']
})
export class RegistationFormComponent {
  onSubmit(form: NgForm){
    console.log(form.value);
  }
}
