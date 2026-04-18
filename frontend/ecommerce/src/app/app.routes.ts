import { Routes } from '@angular/router';
import { HomeComponent } from './features/products/pages/home-component/home-component';
import { LoginComponent } from './features/auth/pages/login-component/login-component';
import { OrderComponent } from './features/orders/pages/order-component/order-component';
import { RegisterComponent } from './features/auth/pages/register-component/register-component';
import { isLoggedInGuard } from './core/guards/is-logged-in-guard';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'order', component: OrderComponent, canActivate: [isLoggedInGuard] },
    { path: 'register', component: RegisterComponent },
];
