import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ocorrencia } from '../models/ocorrencia.model';

@Injectable({
  providedIn: 'root',
})
export class OcorrenciaService {
  private apiUrl = 'http://localhost:8081/ocorrencia';

  constructor(private http: HttpClient) {}

  listar(): Observable<Ocorrencia[]> {
    return this.http.get<Ocorrencia[]>(`${this.apiUrl}/listar`);
  }

  salvar(ocorrencia: Ocorrencia): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/salvar`, ocorrencia);
  }

  atualizar(ocorrencia: Ocorrencia): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/atualizar`, ocorrencia);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${id}`);
  }
}
