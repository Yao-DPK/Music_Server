import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';
import { inject } from '@angular/core';

export const loginGuard: CanActivateFn = () => {
  const router = inject(Router);
  const tokenService = inject(TokenService);
  const token = tokenService.getAccessToken();

  // If user already authenticated → go home
  if (token && !tokenService.isTokenExpired(token)) {
    router.navigate(['/home']);
    return false;
  }

  // Otherwise → allow access to login page
  return true;
};
