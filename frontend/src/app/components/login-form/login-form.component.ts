import { Component, inject, signal } from '@angular/core';
import {ReactiveFormsModule, FormControl, FormGroup, Validators, ValidatorFn, AbstractControl, ValidationErrors} from '@angular/forms'
import { catchError, pipe } from 'rxjs';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast.service';
import { ToastComponent } from "../toast/toast.component";

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss',
  providers:[]
})
export class LoginFormComponent {
  
  
  isLogin = signal(true);
  message = signal('');
  showsToast = signal(false);

  private router = inject(Router);

  loginForm = new FormGroup({
    username : new FormControl('', 
      Validators.required,
    ),
    password : new FormControl('', 
      Validators.required,)
  })  

  username = this.loginForm.get('username');
  valid = this.loginForm.valid;

  registerForm = new FormGroup({
    username : new FormControl('', [
      Validators.required,
    ]),
    password : new FormControl('', [
      Validators.required,
    ]),
    confirmPassword : new FormControl('', [
      Validators.required,
    ]),
  }, {validators: MustMatch}) 

  ChangeView(){
    this.isLogin.update((val) => !val);
    this.message.set('');
    this.loginForm.reset();
    this.registerForm.reset();
  }
  

  onLogin(){
    console.log("Login");
  }

  onRegister(){
    console.log("Register");
  }

  toastService = inject(ToastService)

  
  showSuccess(message: string) {
    this.toastService.show(message, 'success');
  }
  
  showError(message: string) {
    this.toastService.show(message, 'error');
  }

}

export const MustMatch: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
  const password = group.get('password')?.value;
  const confirmPassword = group.get('confirmPassword')?.value;
  return password && confirmPassword && password !== confirmPassword ? { mustMatch: true } : null;
};