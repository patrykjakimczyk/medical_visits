import { Component, OnInit, OnDestroy } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Subscription } from 'rxjs';


import { AuthenticationService } from "./service/authentication.service"
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  public userLogged: boolean;
  private loggedUserSubscription: Subscription;

  constructor(private authService: AuthenticationService, private titleService: Title, private router: Router) {}

  ngOnInit() {
    this.titleService.setTitle('MedicalVisits');
    this.loggedUserSubscription = this.authService.loggedUser.subscribe((user) => {
      // console.log(user);
      if (user === undefined || Object.keys(user).length === 0) {
        this.userLogged = false;
      } else {
        this.userLogged = true;
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(["/"]);
  }

  ngOnDestroy(): void {
    this.loggedUserSubscription.unsubscribe();
  }
}
