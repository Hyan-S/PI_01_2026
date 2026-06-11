import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioRequest } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8081/api/usuarios';

  constructor(private http: HttpClient) {}

  cadastrar(usuario: UsuarioRequest): Observable<any> {
    return this.http.post(this.apiUrl, usuario);
  }

  // Busca a lista real do banco
  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Busca um usuário específico para preencher o form de edição
  buscarPorId(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  editar(id: string, usuario: UsuarioRequest): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, usuario);
  }
}