import { Component, EventEmitter, Output } from '@angular/core';
import { InputComponent } from '../../../../shared/input-component/input-component';
import { ButtonComponent } from '../../../../shared/button-component/button-component';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-form-component',
  imports: [InputComponent, ButtonComponent, ReactiveFormsModule],
  templateUrl: './login-form-component.html',
  styleUrl: './login-form-component.css',
})
export class LoginFormComponent {

  loginForm!: FormGroup;
  emailControl!: FormControl;
  passwordControl!: FormControl;
  @Output() loginSubmit = new EventEmitter<void>();

  ngOnInit(): void {
    this.emailControl = new FormControl('', [Validators.required, Validators.email]);
    this.passwordControl = new FormControl('', [Validators.required, Validators.minLength(6)]);
    this.loginForm = new FormGroup({
      email: this.emailControl,
      password: this.passwordControl
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      console.log('Login submitted:', this.loginForm.value);
      this.loginSubmit.emit(this.loginForm.value);
    }
  }
}
