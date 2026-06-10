import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { LoginResponse, UsuarioSession } from '../models/usuario.model';

@Injectable({
  providedIn: 'root' // Singleton nativo, limpo e gerenciado pelo Angular
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/auth';
  
  // Mantém o estado reativo do usuário logado
  private usuarioLogadoSubject = new BehaviorSubject<UsuarioSession | null>(this.recuperarSessao());
  public usuarioLogado$ = this.usuarioLogadoSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credenciais: { login: string; senha: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credenciais).pipe(
      tap((res) => {
        // Só salva no storage se estiver rodando no navegador do usuário
        if (this.isBrowser()) {
          localStorage.setItem('TOKEN', res.token);
          localStorage.setItem('USER', JSON.stringify(res.usuario));
        }
        
        // O Subject sempre é atualizado, independente de ser servidor ou navegador
        this.usuarioLogadoSubject.next(res.usuario);
      })
    );
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem('TOKEN');
      localStorage.removeItem('USER');
    }
    this.usuarioLogadoSubject.next(null);
  }

  getUsuarioSessao(): UsuarioSession | null {
    return this.usuarioLogadoSubject.value;
  }

  obterToken(): string | null {
    if (this.isBrowser()) {
      return localStorage.getItem('TOKEN');
    }
    return null;
  }

  private recuperarSessao(): UsuarioSession | null {
    if (this.isBrowser()) {
      const user = localStorage.getItem('USER');
      return user ? JSON.parse(user) : null;
    }
    return null;
  }

  /**
   * Helper que garante que o código não vai quebrar no ambiente Node.js (SSR)
   * ao tentar acessar APIs exclusivas do navegador como o Window e LocalStorage.
   */
  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }
}