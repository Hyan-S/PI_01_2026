import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ambulancia } from '../models/ambulancia.model';

@Injectable({
  providedIn: 'root',
})
export class AmbulanciaService {
  private apiUrl = 'http://localhost:8081/ambulancia';

  constructor(private http: HttpClient) {}

  listar(): Observable<Ambulancia[]> {
    return this.http.get<Ambulancia[]>(`${this.apiUrl}/listar`);
  }

  salvar(ambulancia: Ambulancia): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/salvar`, ambulancia);
  }

  atualizar(ambulancia: Ambulancia): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/atualizar`, ambulancia);
  }
  buscarPorId(id: string): Observable<Ambulancia> {
    return this.http.get<Ambulancia>(`${this.apiUrl}/${id}`);
  }
  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${id}`);
  }
}
