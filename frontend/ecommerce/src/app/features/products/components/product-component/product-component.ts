import { Component, Input, inject } from '@angular/core';
import { Product } from '../../models/product';
import { CartService } from '../../../cart/services/cart-service';

@Component({
  selector: 'app-product-component',
  imports: [],
  templateUrl: './product-component.html',
  styleUrl: './product-component.css',
})
export class ProductComponent {
  @Input() product!: Product;

  cartService = inject(CartService);

  addToCart(): void {
    this.cartService.addToCart(this.product);
  }
}
