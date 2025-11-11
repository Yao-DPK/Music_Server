// src/app/services/token.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, switchMap, tap, throwError } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class TokenService {
  private accessToken: string | null = null;
  private refreshTokenKey = 'refresh_token';
  private apiUrl = 'http://localhost:8087/api/v1/auth'; // adjust
  private readonly ACCESS_TOKEN_KEY = 'access_token';

  isRefreshing = false;
  private tokenRefreshed$ = new BehaviorSubject<string | null>(null);

  constructor(private http: HttpClient) {}

  setTokens(access: string, refresh: string) {
    this.accessToken = access;
    console.log("Accesstoken:", this.accessToken);
    localStorage.setItem(this.refreshTokenKey, refresh);
  }

  getAccessToken() {
    console.log("Accesstoken:", this.accessToken);
    return this.accessToken;
  }

  getRefreshToken() {
    return localStorage.getItem(this.refreshTokenKey);
  }

  clearTokens() {
    this.accessToken = null;
    localStorage.removeItem(this.refreshTokenKey);
    console.log("Accesstoken:", this.accessToken);
  }

  setAccessToken(token: string): void {
    /* localStorage.setItem(this.ACCESS_TOKEN_KEY, token); */
    console.log("Accesstoken:", this.accessToken);
    this.accessToken = token;
  }

  /* getAccessToken(): string | null {
    return localStorage.getItem(this.ACCESS_TOKEN_KEY);
  } */

    isTokenExpired(token: string): boolean {
      try {
        const decoded: any = jwtDecode(token);
        const now = Math.floor(Date.now() / 1000);
        return decoded.exp < now;
      } catch {
        return true;
      }
    }

  refreshToken():Observable<any> {
    if (this.isRefreshing) return this.tokenRefreshed$.asObservable();

    this.isRefreshing = true;

    return this.http.post<{ access_token: string }>(
      `${this.apiUrl}/refresh`,
      { withCredentials: true }
    ).pipe(
      tap(res => {
        this.accessToken = res.access_token;
        this.tokenRefreshed$.next(res.access_token);
        this.isRefreshing = false;
      }),
      catchError(err => {
        this.isRefreshing = false;
        this.clearTokens();
        return throwError(() => err);
      })
    );
  }
}
