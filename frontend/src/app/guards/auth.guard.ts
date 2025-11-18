import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TokenService } from '../services/token.service';
import { catchError, map, of } from 'rxjs';
import { KeycloakService } from '../services/keycloak/keycloak.service';
import { isPlatformBrowser } from '@angular/common';

export const authGuard: CanActivateFn = () => {
  const keycloakService = inject(KeycloakService);
  const authService = inject(AuthService);
  const router = inject(Router);

  if (isPlatformBrowser(keycloakService['platformId'])) {
    if (!keycloakService.keycloak || keycloakService.keycloak.isTokenExpired()) {
        return router.navigate(['/login']);
    }
}

return true;
};
