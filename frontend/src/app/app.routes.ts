import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { loginGuard } from './guards/login.guard';

export const routes: Routes = [{
    path:'home',
    pathMatch: 'full',
    loadComponent: () => {
        return import ('./pages/home/home.component').then((m) => m.HomeComponent)
    },
    canActivate: [authGuard]
},
/* {
    path: 'login',
    pathMatch:'full',
    loadComponent: () => {
        return import ('./pages/login/login.component').then((m) => m.LoginComponent)
    }, 
    canActivate: [loginGuard]
}, */
{
    path: 'test',
    pathMatch:'full',
    loadComponent: () => {
        return import ('./pages/test-pages/test-pages.component').then((m) => m.TestPagesComponent)
    }, 
    canActivate: [authGuard]
},
{ path: '**', redirectTo: 'home' }

];
