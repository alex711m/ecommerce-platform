import { Component, inject } from '@angular/core';
import { LoginFormComponent } from '../../components/login-form-component/login-form-component';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-login-component',
  imports: [LoginFormComponent, RouterLink],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  authService = inject(AuthService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  onLoginSubmit(loginData: any) {
    console.log('Login submitted:', loginData);
    this.authService.login(loginData.email, loginData.password).subscribe({
      next: () => {
        console.log("returnUrl: " + this.route.snapshot.queryParams['returnUrl']);
        const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/home';
        console.log("returnUrl: " + returnUrl);
        console.log("redirect to " + returnUrl);
        this.router.navigate([returnUrl]);
        console.log('Login success');
      },
      error: (error) => {
        console.error('Login error:', error);
      }
    });
  }
}
