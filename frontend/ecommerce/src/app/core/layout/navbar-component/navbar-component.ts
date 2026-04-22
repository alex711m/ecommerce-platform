import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CartService } from '../../../features/cart/services/cart-service';

@Component({
  selector: 'app-navbar-component',
  imports: [RouterLink],
  templateUrl: './navbar-component.html',
  styleUrl: './navbar-component.css',
})
export class NavbarComponent {
  cartService = inject(CartService);

  toggleCart(): void {
    this.cartService.toggleCart();
  }
}
