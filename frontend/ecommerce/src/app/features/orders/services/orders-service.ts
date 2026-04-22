import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  apiUrl = environment.orderServiceUrl;
  http = inject(HttpClient);


  placeOrder(id: number, productId: number, quantity: number) {
    return this.http.post(
      this.apiUrl,
      { customerId: id, productId: productId, quantity: quantity },
      { responseType: 'text' }
    );
  }


}
