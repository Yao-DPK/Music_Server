import { Component, inject, signal } from '@angular/core';
import {ReactiveFormsModule, FormControl, FormGroup, Validators, ValidatorFn, AbstractControl, ValidationErrors} from '@angular/forms'
import { LoginService } from '../../services/login.service';
import { catchError, pipe } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss',
  providers:[LoginService]
})
export class LoginFormComponent {

  isLogin = signal(true);
  message = signal('');
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
    this.loginForm.reset();
    this.registerForm.reset();
  }
  

  onLogin(){
    this.loginService
    .login(this.loginForm.value.username!, this.loginForm.value.password!)
    .pipe(
      catchError((err) => {
        console.log(err.error.message);
        this.message.set(err.error.message);
        throw err;
      })
    )
    .subscribe((res) => {
      console.log("Resultat: ", res)
      this.router.navigate(['/home'])
    })
  }

  onRegister(){
    this.loginService
    .register(this.registerForm.value.username!, this.registerForm.value.password!)
    .pipe(
      catchError((err) => {
        console.log(err);
        throw err;
      })
    )
    .subscribe((res) => {
      console.log("Resultat: ", res)
      this.isLogin.set(true)
    })
  }



  loginService = inject(LoginService)


}

export const MustMatch: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
  const password = group.get('password')?.value;
  const confirmPassword = group.get('confirmPassword')?.value;
  return password && confirmPassword && password !== confirmPassword ? { mustMatch: true } : null;
};