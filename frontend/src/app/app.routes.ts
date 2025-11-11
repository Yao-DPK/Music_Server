import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [{
    path:'home',
    pathMatch: 'full',
    loadComponent: () => {
        return import ('./pages/home/home.component').then((m) => m.HomeComponent)
    },
    canActivate: [authGuard]
},
{
    path: 'login',
    pathMatch:'full',
    loadComponent: () => {
        return import ('./pages/login/login.component').then((m) => m.LoginComponent)
    }, 
},
{ path: '**', redirectTo: 'login' }

];
