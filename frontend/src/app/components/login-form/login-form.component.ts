import { Component, inject, signal } from '@angular/core';
import {ReactiveFormsModule, FormControl, FormGroup, Validators, ValidatorFn, AbstractControl, ValidationErrors} from '@angular/forms'
import { LoginService } from '../../services/login.service';
import { catchError, pipe } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { ToastComponent } from "../toast/toast.component";
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss',
  providers:[AuthService]
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
    this.authService
    .login(this.loginForm.value.username!, this.loginForm.value.password!)
    .pipe(
      catchError((err) => {
        console.log(err.error.message);
        this.message.set(err.error.message);
        this.showError(err.error.message);
        throw err;
      })
    )
    .subscribe((res) => {
      console.log("Resultat: ", res);
      this.tokenService.setAccessToken(res.token);
      this.showSuccess("Login Successful");
      this.router.navigate(['/home'])
    })
  }

  onRegister(){
    this.authService
    .register(this.registerForm.value.username!, this.registerForm.value.password!)
    .pipe(
      catchError((err) => {
        console.log(err);
        this.showError(err.error.message);
        throw err;
      })
    )
    .subscribe((res) => {
      console.log("Resultat: ", res)
      this.showSuccess("Registration Successful, You may login");
      this.isLogin.set(true)
    })
  }

  onLogout(){
    this.authService.logout();
  }

  authService = inject(AuthService)
  toastService = inject(ToastService)
  tokenService = inject(TokenService)
  
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