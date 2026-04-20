import { computed, Injectable, signal } from '@angular/core';
import { CartItem } from '../models/cart-item';
import { Product } from '../../products/models/product';

@Injectable({
  providedIn: 'root',
})
export class CartService {

  cart = signal<CartItem[]>([]);
  isOpen = signal<boolean>(false);
  total = computed(() => this.cart().reduce((acc, item) => acc + item.price * item.quantity, 0));
  totalItems = computed(() => this.cart().reduce((acc, item) => acc + item.quantity, 0));

  addToCart(product: Product): void {
    const existingItem = this.cart().find((item) => item.id === product.id);
    if (existingItem) {
      // ⚠️ On NE mute PAS l'objet directement (existingItem.quantity++ ne déclenche
      // pas la réactivité du signal). On retourne un nouveau tableau avec un nouvel objet.
      this.cart.update((items) =>
        items.map((item) =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        )
      );
    } else {
      this.cart.update((items) => [...items, { ...product, quantity: 1 }]);
    }
  }

  removeFromCart(id: number): void {
    this.cart.update((items) => items.filter((item) => item.id !== id));
  }

  updateQuantity(id: number, quantity: number): void {
    if (quantity <= 0) {
      this.removeFromCart(id);
      return;
    }
    this.cart.update((items) =>
      items.map((item) => (item.id === id ? { ...item, quantity } : item))
    );
  }

  clearCart(): void {
    this.cart.set([]);
  }

  toggleCart(): void {
    this.isOpen.update((value) => !value);
  }
}
