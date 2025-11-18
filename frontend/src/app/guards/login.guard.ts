import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { KeycloakService } from '../services/keycloak/keycloak.service';

export const loginGuard: CanActivateFn = () => {
  const router = inject(Router);
  const keycloakService = inject(KeycloakService);

  // If user already authenticated → go home
  if (!keycloakService.keycloak?.isTokenExpired()) {
    router.navigate(['/home']);
    return false;
  }

  // Otherwise → allow access to login page
  return true;
};
