import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { TokenService } from './token.service';


export function initializeAuth(authService: AuthService) {
  return () => authService.refreshToken();
}


@Injectable({ providedIn: 'root' })
export class AuthService {
  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedIn$.asObservable();
  tokenService = inject(TokenService);

  constructor(private http: HttpClient) {
    this._isLoggedIn$.next(!!this.getAccessToken());
  }

  login(username: string, password: string) {

    

    return this.http.post<{ token: string }>(
      `${environment.apiUrl}/auth/login`,
      { username, password },
      { withCredentials: true } // ⬅ sends + receives cookies
    ).pipe(
      tap(response => {
        this.setAccessToken(response.token);
        this._isLoggedIn$.next(true);
      })
    );
  }

  register(username: string, password: string){
    console.log("Register 2");

    return this.http.post<{ accessToken: string }>(`${environment.apiUrl}/auth/register`, {
      username: username,
      password: password
    },
    { withCredentials: true } // ⬅ sends + receives cookies
  );
  }

  refreshToken(): Observable<any>  {
    return this.tokenService.refreshToken();
  }

  logout() {
    this.clearAccessToken();
    this._isLoggedIn$.next(false);
  }

  getAccessToken(): string | null {
    return this.tokenService.getAccessToken();
  }

  setAccessToken(token: string) {
    return this.tokenService.setAccessToken(token);
  }

  clearAccessToken() {
    this.tokenService.clearTokens()
    
  }
}
