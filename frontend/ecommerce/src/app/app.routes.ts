import { Routes } from '@angular/router';
import { HomeComponent } from './features/products/pages/home-component/home-component';
import { OrderComponent } from './features/orders/pages/order-component/order-component';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    { path: 'order', component: OrderComponent },
    { path: '**', redirectTo: 'home', pathMatch: 'full' },
];
