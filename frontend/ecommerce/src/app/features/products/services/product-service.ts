import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Product } from '../models/product';

// Type brut retourné par FakeStore API
interface FakeStoreProduct {
  id: number;
  title: string;
  price: number;
  description: string;
  image: string;
  category: string;
  rating: { rate: number; count: number };
}

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly apiUrl = 'https://fakestoreapi.com/products';
  private http = inject(HttpClient);

  getProducts(): Observable<Product[]> {
    return this.http.get<FakeStoreProduct[]>(this.apiUrl).pipe(
      map((products) =>
        products.map((p) => ({
          id: p.id,
          name: p.title,         // FakeStore utilise "title", notre interface utilise "name"
          price: p.price,
          description: p.description,
          image: p.image,
          category: p.category,
          rating: p.rating.rate, // On ne garde que le score numérique
        }))
      )
    );
  }
}
