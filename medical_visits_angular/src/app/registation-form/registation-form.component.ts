import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-registation-form',
  templateUrl: './registation-form.component.html',
  styleUrls: ['./registation-form.component.css']
})
export class RegistationFormComponent {
  calculateBirthdate(form: NgForm) {
    const pesel = form.value.pesel;
    if (pesel && pesel.length === 11) {
      const weights = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
      let sum = 0;
      for (let i = 0; i < 10; i++) {
        sum += Number(pesel.charAt(i)) * weights[i];
      }
      const controlNumber = (10 - (sum % 10)) % 10;
      if (controlNumber === Number(pesel.charAt(10))) {
        let year = Number(pesel.substring(0, 2));
        let month = Number(pesel.substring(2, 4));
        let day = Number(pesel.substring(4, 6));
        let century = 19;
        if (month > 20 && month < 33) {
          century = 20;
          month -= 20;
        } else if (month > 40 && month < 53) {
          century = 21;
          month -= 40;
        } else if (month > 60 && month < 73) {
          century = 22;
          month -= 60;
        } else if (month > 80 && month < 93) {
          century = 18;
          month -= 80;
        }
        const fullYear = century * 100 + year;
        const birthdate = new Date(fullYear, month - 1, day + 1);
        const age = Math.floor((Date.now() - birthdate.getTime()) / (1000 * 60 * 60 * 24 * 365));

        if(age < 18){
          alert("Nie jesteś pełnoletni nie możesz się zarejestrować w MedicalVisits")
          form.controls['pesel'].setErrors({ 'invalid': true });
          return;
        }

        form.controls['dob'].setValue(birthdate.toISOString().substring(0, 10));
        form.controls['age'].setValue(age);
      } else {
        form.controls['pesel'].setErrors({ 'invalid': true });
      }
    }
  }

  validatePasswords(form: NgForm) {
    const password = form.controls['password'].value;
    const confirmPassword = form.controls['confirmPassword'].value;
    if (password !== confirmPassword) {
      form.controls['confirmPassword'].setErrors({ notMatch: true });
    } else {
      form.controls['confirmPassword'].setErrors(null);
    }
  }

  onSubmit(form: NgForm){
    console.log(form.value);
  }
}
