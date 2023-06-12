import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { User } from '../model/user';
import { map } from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';



@Injectable({ providedIn: 'root' })
export class AuthGuard {
  jwtHelper: JwtHelperService;
    constructor(
        private router: Router,
        private authService: AuthenticationService
    ) {
      this.jwtHelper = new JwtHelperService();
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      return this.authService.loggedUser.pipe(
        map((user: User | undefined) => {
          if (user !== undefined ) {
            if(this.jwtHelper.isTokenExpired(user.token)) {
              this.authService.logout();
              this.router.navigate(["/"]);
              return false;
            }

          const expectedRole = route.data['expectedRole'];

            if (user.role === expectedRole) {
              return true;
            }
          }

          this.router.navigate(['/']);
          return false;
        })
      );
    }
}
