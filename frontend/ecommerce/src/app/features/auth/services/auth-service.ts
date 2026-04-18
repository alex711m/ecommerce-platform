import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = 'https://fakestoreapi.com/auth/login';
  private http = inject(HttpClient);

  login(username: string, password: string): Observable<any> {
    //store false token
    localStorage.setItem('tokenID', 'fake-token');
    return of({ token: 'fake-token' });
  }

  register(username: string, password: string): Observable<any> {
    return of({ token: 'fake-token' });
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('tokenID');
  }

  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }
}
