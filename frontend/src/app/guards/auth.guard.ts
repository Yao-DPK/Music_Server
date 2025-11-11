import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TokenService } from '../services/token.service';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenService);
  const authService = inject(AuthService);
  const token = tokenService.getAccessToken();

  if (!tokenService.isTokenExpired(token!)) {
    return true; // token valid, allow navigation
  }

  // token expired — try silent refresh
  return authService.refreshToken().pipe(
    map(() => true),
    catchError(() => {
      authService.logout();
      return of(false);
    })
  );
};