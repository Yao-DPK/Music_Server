import { inject, Injectable } from '@angular/core';
import { LoginRequest } from '../../../models/login.type';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class LoginService {

  http = inject(HttpClient)
  constructor() { }

  register(username: string|null, password: string|null){
    console.log("Register 2");

    return this.http.post('http://localhost:8087/api/v1/auth/register', {
      username: username,
      password: password
    })
  }

  login(username: string|null, password: string|null,){
    console.log("Login 2");
    

    return this.http.post('http://localhost:8087/api/v1/auth/login', {
      username: username,
      password: password
    })
  }
}
