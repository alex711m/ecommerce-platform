import { Component, Input } from '@angular/core';
import { ProductComponent } from '../product-component/product-component';
import { Product } from '../../models/product';

@Component({
  selector: 'app-product-list-component',
  imports: [ProductComponent],
  templateUrl: './product-list-component.html',
  styleUrl: './product-list-component.css',
})
export class ProductListComponent {
  @Input() products: Product[] = [];
}
