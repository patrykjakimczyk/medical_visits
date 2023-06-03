import { Component } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent {
  public userLogged: boolean;
  public userRole: string | undefined;
  private loggedUserSubscription: Subscription;

  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.loggedUserSubscription = this.authService.loggedUser.subscribe((user) => {
      if (user === undefined || Object.keys(user).length === 0) {
        this.userLogged = false;
        this.userRole = undefined;
      } else {
        this.userLogged = true;
        this.userRole = user?.role;
      }
    });
  }

  ngOnDestroy(): void {
    this.loggedUserSubscription.unsubscribe();
  }
}
