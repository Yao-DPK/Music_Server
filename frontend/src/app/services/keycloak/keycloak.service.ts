import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import Keycloak from 'keycloak-js'; // Import KeyCloak from the keycloak-js library
import { UserProfile } from './user-profile';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private _keycloak: Keycloak | undefined;
  private _profile: UserProfile | undefined;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) { }

  async init(){
    console.log("Initialising Keycloak");
    if(isPlatformBrowser(this.platformId)){
      const authenticated = await this.keycloak.init({
      onLoad: 'login-required',
    })
    
    if(authenticated){
      //console.log('User Authenticated');
      this._profile = (await this.keycloak?.loadUserProfile()) as UserProfile;
      this._profile.token = this._keycloak?.token;
    }
  } else{
    return Promise.resolve();
  }
  }


  login() { 
    if(isPlatformBrowser(this.platformId)){
      this.keycloak?.login(); 
    }
    
  }


  logout() { 
    if(isPlatformBrowser(this.platformId)){
      this.keycloak?.logout({redirectUri: "http://localhost:4200"}); 
    }    
   }

   accountManagement() { 
    if(isPlatformBrowser(this.platformId)){
      this.keycloak?.accountManagement(); 
    }    
   }

   updateTokenIfNeeded(): Observable<void> {
    return new Observable(observer => {
      if (!isPlatformBrowser(this.platformId) || !this.keycloak) {
        observer.complete();
        return;
      }

      this.keycloak.updateToken(30)
        .then(() => { observer.next(); observer.complete(); })
        .catch(() => { this.keycloak?.login(); observer.error('Token refresh failed'); });
    });
  }

  getUserName(){
    if (!isPlatformBrowser(this.platformId) || !this.keycloak) {
      return;
    }
    return this.keycloak.tokenParsed?this.keycloak.tokenParsed["preferred_username"]: '';
    
  }
  
  get keycloak(){
    if(!this._keycloak){
      this._keycloak = new Keycloak({
        url: 'http://localhost:9097',
        realm: 'music-app',
        clientId: 'ms'
      });
    }
    return this._keycloak;
  }

  get profile(): UserProfile | undefined{
    return this._profile;
  }
}
