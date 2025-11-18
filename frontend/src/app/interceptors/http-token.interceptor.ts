import { inject, Injectable, Injector } from '@angular/core';
import {
  HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, switchMap, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { TokenService } from '../services/token.service';
import { Router } from '@angular/router';
import { KeycloakService } from '../services/keycloak/keycloak.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private keycloakService = inject(KeycloakService);
  constructor() {
    console.log('AuthInterceptor instantiated');
  }
  

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.keycloakService.updateTokenIfNeeded().pipe(
      switchMap(() => {
        const kc = this.keycloakService.keycloak;
        const token = kc?.token;
        //console.log('JWT being sent:', token);

        const cloned = token
          ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
          : req;

        return next.handle(cloned);
      })
    );
  }
}