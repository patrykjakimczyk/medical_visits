import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

import { AuthenticationService } from '../service/authentication.service';


@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css', '../form/registation-form/registation-form.component.css']
})
export class HomePageComponent implements OnInit, OnDestroy{
  public userLogged: boolean;
  public mainButtonText: string;
  private loggedUserSubscription: Subscription;

  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.loggedUserSubscription = this.authService.loggedUser.subscribe((user) => {
      // console.log(user);
      if (user === undefined || Object.keys(user).length === 0) {
        this.userLogged = false;
        this.mainButtonText = "START HERE";
      } else {
        this.userLogged = true;
        this.mainButtonText = "BOOK YOUR APPOINTMENT";
      }
    });
  }

  logout() {
    this.authService.logout();
    this.userLogged = false;
    this.mainButtonText = "START HERE"
  }

  ngOnDestroy(): void {
    this.loggedUserSubscription.unsubscribe();
  }
}
