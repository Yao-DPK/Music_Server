import { inject, Injectable } from '@angular/core';
import { LoginRequest } from '../../../models/login.type';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable()
export class AuthService {

  http = inject(HttpClient)
  constructor() { }

  apiUrl = environment.apiUrl;

  register(username: string|null, password: string|null){
    console.log("Register 2");

    return this.http.post(`${this.apiUrl}/auth/register`, {
      username: username,
      password: password
    })
  }

  login(username: string|null, password: string|null,){
    console.log("Login 2");
    

    return this.http.post(`${this.apiUrl}/auth/login`, {
      username: username,
      password: password
    })
  }
}
