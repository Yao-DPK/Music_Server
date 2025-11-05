import { Component, inject, OnInit } from '@angular/core';
import { LoginFormComponent } from "../../components/login-form/login-form.component";
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  imports: [LoginFormComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  providers:[LoginService]
})
export class LoginComponent{

}
