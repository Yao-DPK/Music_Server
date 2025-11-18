/* import { Component, Inject, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { LoginFormComponent } from "../../components/login-form/login-form.component";
import { LoginService } from '../../services/login.service';
import { ToastComponent } from "../../components/toast/toast.component";
import { KeycloakService } from '../../services/keycloak/keycloak.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent implements OnInit{

  constructor(@Inject(PLATFORM_ID) private platformId: Object, private keycloakService: KeycloakService) {}

  async ngOnInit(): Promise<void> {

    if (isPlatformBrowser(this.platformId))  {
      await this.keycloakService.init();
      await this.keycloakService.login()
    } else{
      Promise.resolve();
    }
  }

}
 */