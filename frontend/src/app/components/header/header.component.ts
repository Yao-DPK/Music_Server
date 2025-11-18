import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TokenService } from '../../services/token.service';
import { AuthService } from '../../services/auth.service';
import { KeycloakService } from '../../services/keycloak/keycloak.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  title=signal("pyke's player");
  tokenService = inject(TokenService);
  authService = inject(AuthService)

  constructor(private keycloakService: KeycloakService ) {}

  async onLogout(){
    this.keycloakService.logout();
  }

  async onAccount(){
    this.keycloakService.accountManagement();
  }
}
