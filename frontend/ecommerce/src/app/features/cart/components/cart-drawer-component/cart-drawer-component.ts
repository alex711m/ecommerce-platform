import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CartItemComponent } from '../cart-item-component/cart-item-component';
import { CartService } from '../../services/cart-service';

@Component({
  selector: 'app-cart-drawer-component',
  imports: [CartItemComponent],
  templateUrl: './cart-drawer-component.html',
  styleUrl: './cart-drawer-component.css',
})
export class CartDrawerComponent {
  cartService = inject(CartService);
  private router = inject(Router);

  goToCheckout(): void {
    this.cartService.isOpen.set(false);
    this.router.navigate(['/order']);
  }
}
