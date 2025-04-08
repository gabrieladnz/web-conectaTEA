// Libs
import { Routes } from '@angular/router';

// Components
import { RegisterComponent } from './modules/register/register.component';
import { LoginComponent } from './modules/login/login.component';
import { DashboardComponent } from './modules/dashboard/dashboard.component';
import { ChatComponent } from './modules/chat/chat.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/auth/login',
        pathMatch: 'full',
    },
    {
        path: 'auth',
        children: [
            {
                path: 'login',
                component: LoginComponent,
            },
            {
                path: 'register',
                component: RegisterComponent,
            },
        ],
    },
    {
        path: 'dashboard',
        component: DashboardComponent,
    },
    {
        path: 'chat',
        component: ChatComponent,
    },
    {
        path: '**',
        redirectTo: '/auth/login',
    },
];
