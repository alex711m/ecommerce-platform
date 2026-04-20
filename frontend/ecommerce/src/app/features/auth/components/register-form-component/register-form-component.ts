import { Component, EventEmitter, Output } from '@angular/core';
import { InputComponent } from '../../../../shared/input-component/input-component';
import { ButtonComponent } from '../../../../shared/button-component/button-component';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-register-form-component',
  imports: [InputComponent, ButtonComponent, ReactiveFormsModule],
  templateUrl: './register-form-component.html',
  styleUrl: './register-form-component.css',
})
export class RegisterFormComponent {

  nameControl!: FormControl;
  emailControl!: FormControl;
  passwordControl!: FormControl;
  registerForm!: FormGroup;
  @Output() registerSubmit = new EventEmitter<void>();

  ngOnInit(): void {
    this.nameControl = new FormControl('', [Validators.required]);
    this.emailControl = new FormControl('', [Validators.required, Validators.email]);
    this.passwordControl = new FormControl('', [Validators.required, Validators.minLength(6)]);
    this.registerForm = new FormGroup({
      name: this.nameControl,
      email: this.emailControl,
      password: this.passwordControl
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      console.log('Register submitted:', this.registerForm.value);
      this.registerSubmit.emit(this.registerForm.value);
    }
  }
}
