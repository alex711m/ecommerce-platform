import { Component, inject, signal } from '@angular/core';
import { ProductListComponent } from '../../components/product-list-component/product-list-component';
import { CartDrawerComponent } from '../../../cart/components/cart-drawer-component/cart-drawer-component';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product-service';

@Component({
  selector: 'app-home-component',
  imports: [ProductListComponent, CartDrawerComponent],
  templateUrl: './home-component.html',
  styleUrl: './home-component.css',
})
export class HomeComponent {
  productService = inject(ProductService);
  products = signal<Product[]>([]);

  ngOnInit() {
    this.productService.getProducts().subscribe((products) => {
      this.products.set(products);
      console.log(this.products());
    });
  }
}
