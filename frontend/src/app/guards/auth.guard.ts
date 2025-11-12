import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TokenService } from '../services/token.service';
import { catchError, map, of } from 'rxjs';

export const authGuard: CanActivateFn = () => {
  const tokenService = inject(TokenService);
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = tokenService.getAccessToken();

  // ✅ Token valid
  if (token && !tokenService.isTokenExpired(token)) {
    return true;
  }

  if (token && tokenService.isTokenExpired(token)) {
    // ⚙️ Token expired → try refresh
    return authService.refreshToken().pipe(
      map(() => true),
      catchError(() => {
        authService.logout();
        router.navigate(['/login']);
        return of(false);
      })
    );
  }

  router.navigate(['/login']);
    return false;
};
