import { Component, computed, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { CartService } from '../../../cart/services/cart-service';
import { OrdersService } from '../../services/orders-service';
import { CurrencyPipe } from '@angular/common';

/** Valide que la chaîne ne contient que des chiffres (pour le numéro de carte, CVV…) */
function digitsOnly(control: AbstractControl): ValidationErrors | null {
  return /^\d+$/.test(control.value ?? '') ? null : { digitsOnly: true };
}

/** Valide le format MM/AA */
function expiryFormat(control: AbstractControl): ValidationErrors | null {
  return /^(0[1-9]|1[0-2])\/\d{2}$/.test(control.value ?? '')
    ? null
    : { expiryFormat: true };
}

@Component({
  selector: 'app-order-component',
  imports: [ReactiveFormsModule, CurrencyPipe],
  templateUrl: './order-component.html',
  styleUrl: './order-component.css',
})
export class OrderComponent {
  private fb = inject(FormBuilder);
  cartService = inject(CartService);
  private ordersService = inject(OrdersService);
  private router = inject(Router);

  orderStatus: 'idle' | 'loading' | 'success' | 'error' = 'idle';
  errorMessage = '';

  form = this.fb.group({
    firstName:  ['', [Validators.required, Validators.minLength(2)]],
    lastName:   ['', [Validators.required, Validators.minLength(2)]],
    address:    ['', [Validators.required, Validators.minLength(5)]],
    isStudent:  [false],
    cardNumber: ['', [Validators.required, Validators.minLength(16), Validators.maxLength(16), digitsOnly]],
    expiry:     ['', [Validators.required, expiryFormat]],
    cvv:        ['', [Validators.required, Validators.minLength(3), Validators.maxLength(4), digitsOnly]],
  });

  /** Signal réactif lié au control isStudent — tracké par computed() */
  private isStudentSignal = toSignal(
    this.form.get('isStudent')!.valueChanges,
    { initialValue: false }
  );

  get isStudent(): boolean {
    return !!this.isStudentSignal();
  }

  subtotal   = computed(() => this.cartService.total());
  discount   = computed(() => this.isStudentSignal() ? this.subtotal() * 0.2 : 0);
  finalTotal = computed(() => this.subtotal() - this.discount());

  /** Helper raccourci pour le template */
  ctrl(name: string) {
    return this.form.get(name)!;
  }

  /** Formate le numéro de carte affiché (groupe de 4) */
  formatCard(event: Event) {
    const input = event.target as HTMLInputElement;
    input.value = input.value.replace(/\D/g, '').slice(0, 16);
    this.ctrl('cardNumber').setValue(input.value, { emitEvent: false });
  }

  /** Formate MM/AA */
  formatExpiry(event: Event) {
    const input = event.target as HTMLInputElement;
    let val = input.value.replace(/\D/g, '').slice(0, 4);
    if (val.length >= 3) val = val.slice(0, 2) + '/' + val.slice(2);
    input.value = val;
    this.ctrl('expiry').setValue(val, { emitEvent: false });
  }

  goBack() {
    this.router.navigate(['/home']);
  }

  placeOrder() {
    if (this.form.invalid || this.cartService.cart().length === 0) {
      this.form.markAllAsTouched();
      return;
    }

    const firstItem = this.cartService.cart()[0];
    const customerId = this.isStudent ? 1 : 2;

    this.orderStatus = 'loading';
    this.ordersService
      .placeOrder(customerId, firstItem.id, firstItem.quantity)
      .subscribe({
        next: () => {
          this.orderStatus = 'success';
          this.cartService.clearCart();
        },
        error: (err) => {
          this.orderStatus = 'error';
          this.errorMessage = err?.message ?? 'Une erreur est survenue.';
        },
      });
  }
}
