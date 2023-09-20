import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { User } from '../model/user';
import { map } from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';



@Injectable()
export class AdminGuard {
    constructor(
        private router: Router,
        private authService: AuthenticationService,
        // private jwtHelper: JwtHelperService
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      return this.authService.loggedUser.pipe(
        map((user: User | undefined) => {
          if (user !== undefined ) {
            if(this.jwtHelper.isTokenExpired(user.token)) {
              this.authService.logout();
              this.router.navigate(["/"]);
              return false;
            }

            if (user.role === "ADMIN") {
              return true;
            }
          }

          this.router.navigate(['/']);
          return false;
        })
      );
    }
}
