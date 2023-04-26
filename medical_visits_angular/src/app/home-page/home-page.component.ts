import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css', '../form/registation-form/registation-form.component.css']
})
export class HomePageComponent implements OnInit, OnDestroy{
  public userLogged: boolean;
  public user: string;
  public mainButtonText: string;
  private loggedUserSubscription: Subscription;

  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.loggedUserSubscription = this.authService.loggedUser.subscribe((user) => {
      console.log(user);
      if (user === undefined || Object.keys(user).length === 0) {
        this.userLogged = false;
        this.user = "";
        this.mainButtonText = "START HERE";
      } else {
        this.userLogged = true;
        this.user = user.role;
        this.mainButtonText = "BOOK YOUR APPOINTMENT";
      }
    });
  }

  logout() {
    this.authService.logout();
    // this.userLogged = false;
    // this.mainButtonText = "START HERE"
  }

  ngOnDestroy(): void {
    this.loggedUserSubscription.unsubscribe();
  }
}
