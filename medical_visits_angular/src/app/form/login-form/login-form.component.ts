import { Component, ViewEncapsulation } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ApiService } from '../../service/api.service';
import { AuthenticationService } from '../../service/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../../model/user';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css', '../registation-form/registation-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginFormComponent {
  protected errorMessage: string;
  protected showError: boolean;

  constructor(
    private apiService: ApiService,
    private authService: AuthenticationService,
    private router: Router
  ) {
    this.errorMessage = '';
    this.showError = false;
  }

  onSubmit(form: NgForm) {
    this.apiService.login(form)
      .subscribe({
        next: (response: User) => {
          this.authService.loginUser(response);
          this.errorMessage = "";
          this.showError = !this.showError;
          this.router.navigate(["/"]);
        },
        error: (error: HttpErrorResponse) => {
          if (error.status === 403) {
            this.errorMessage = "Email or password is incorrect";
            this.showError = !this.showError;
          }
        }
      });
  }
}
