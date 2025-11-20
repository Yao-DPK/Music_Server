import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { KeycloakService } from '../../services/keycloak/keycloak.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  title=signal("pyke's player");

  constructor(private keycloakService: KeycloakService ) {}

  async onLogout(){
    this.keycloakService.logout();
  }

  async onAccount(){
    this.keycloakService.accountManagement();
  }
}
