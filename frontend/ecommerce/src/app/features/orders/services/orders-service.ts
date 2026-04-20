import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  apiUrl = 'http://localhost:8083/api/orders';
  http = inject(HttpClient);


  placeOrder(id: number, productId: number, quantity: number) {
    return this.http.post(this.apiUrl, { customerId: id, productId: productId, quantity: quantity });
  }


}
