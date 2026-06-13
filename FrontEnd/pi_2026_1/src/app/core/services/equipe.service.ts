import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipe } from '../models/equipe.model';

@Injectable({
  providedIn: 'root',
})
export class EquipeService {
  private apiUrl = 'http://localhost:8081/equipe';

  constructor(private http: HttpClient) {}

  listar(): Observable<Equipe[]> {
    return this.http.get<Equipe[]>(`${this.apiUrl}/listar`);
  }

  salvar(equipe: Equipe): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/salvar`, equipe);
  }

  atualizar(equipe: Equipe): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/atualizar`, equipe);
  }

  buscarPorId(id: string): Observable<Equipe> {
    return this.http.get<Equipe>(`${this.apiUrl}/${id}`);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${id}`);
  }
}
