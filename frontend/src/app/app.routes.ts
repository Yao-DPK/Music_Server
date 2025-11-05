import { Routes } from '@angular/router';

export const routes: Routes = [{
    path:'home',
    pathMatch: 'full',
    loadComponent: () => {
        return import ('./pages/home/home.component').then((m) => m.HomeComponent)
    }
},
{
    path: '',
    pathMatch:'full',
    loadComponent: () => {
        return import ('./pages/login/login.component').then((m) => m.LoginComponent)
    }
}
];
