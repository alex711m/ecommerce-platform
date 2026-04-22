import { Component, Input, inject } from '@angular/core';
import { CartItem } from '../../models/cart-item';
import { CartService } from '../../services/cart-service';

@Component({
  selector: 'app-cart-item-component',
  imports: [],
  templateUrl: './cart-item-component.html',
  styleUrl: './cart-item-component.css',
})
export class CartItemComponent {
  @Input() item!: CartItem;
  cartService = inject(CartService);
}
