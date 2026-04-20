import { Component, inject } from '@angular/core';
import { RegisterFormComponent } from '../../components/register-form-component/register-form-component';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-register-component',
  imports: [RegisterFormComponent, RouterLink],
  templateUrl: './register-component.html',
  styleUrl: './register-component.css',
})
export class RegisterComponent {

  authService = inject(AuthService);

  onRegisterSubmit(registerData: any) {
    console.log('Register submitted:', registerData);
    this.authService.register(registerData.email, registerData.password).subscribe({
      next: () => {
        console.log('Register success');
      },
      error: (error) => {
        console.error('Register error:', error);
      }
    });
  }
}
