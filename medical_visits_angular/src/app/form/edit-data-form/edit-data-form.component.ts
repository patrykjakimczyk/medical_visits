import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, NgForm } from '@angular/forms';
import { ApiService } from '../../service/api.service';
import { AuthenticationService } from '../../service/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../model/user';
import { Router } from '@angular/router';
import { ValidationService } from '../../service/validation.service';


@Component({
  selector: 'app-edit-data-form',
  templateUrl: './edit-data-form.component.html',
  styleUrls: ['./edit-data-form.component.css', '../registation-form/registation-form.component.css']
})
export class EditDataFormComponent {
protected errorMessage: string;
  protected showError: boolean;

  constructor(
    private apiService: ApiService,
    private authService: AuthenticationService,
    private router: Router,
    private validationService: ValidationService
  ) {
    this.errorMessage = '';
    this.showError = false;
  }

  gender = [
    {id: '1', value: 'Male'},
    {id: '2', value: 'Female'}
  ];

  calculateBirthdate(form: NgForm) {
    this.validationService.calculateBirthdate(form);
  }

  validatePasswords(form: NgForm) {
    this.validationService.validatePasswords(form);
  }

  onSubmit(form: NgForm){
      this.apiService.registerPatient(form)
        .subscribe({
          next: (response: User) => {
            this.authService.loginUser(response);
            this.errorMessage = "";
            this.showError = !this.showError;
            this.router.navigate(["/"]);
          },
          error: (error: HttpErrorResponse) => {
            if (error.status === 400) {
              this.errorMessage = "Pesel/E-mail/phone number is taken";
              this.showError = !this.showError;
            }
          }
        });
  }
}