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
  public userRole: string | undefined;
  public mainButtonText: string;
  private loggedUserSubscription: Subscription;

  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.loggedUserSubscription = this.authService.loggedUser.subscribe((user) => {
      if (user === undefined || Object.keys(user).length === 0) {
        this.userLogged = false;
        this.mainButtonText = "START HERE";
        this.userRole = undefined;
      } else {
        this.userRole = user?.role;
        this.userLogged = true;
        this.mainButtonText = "BOOK YOUR APPOINTMENT";
        console.log(this.userRole)
      }
    });
  }

  ngOnDestroy(): void {
    this.loggedUserSubscription.unsubscribe();
  }
}
