import { Component, inject, OnInit } from '@angular/core';
import { LoginFormComponent } from "../../components/login-form/login-form.component";
import { LoginService } from '../../services/login.service';
import { ToastComponent } from "../../components/toast/toast.component";

@Component({
  selector: 'app-login',
  imports: [LoginFormComponent, ToastComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent{

}
