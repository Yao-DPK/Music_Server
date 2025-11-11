import { CanActivateFn, Router } from "@angular/router";
import { TokenService } from "../services/token.service";
import { inject } from "@angular/core";

export const loginGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const tokenService = inject(TokenService);
  const token = tokenService.getAccessToken();
  if (tokenService.isTokenExpired(token!)) {
    router.navigate(['/home']);
    return false;
  }
  return true;
};
