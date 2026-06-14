import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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

  salvar(ocorrencia: Ocorrencia): Observable<Ocorrencia> {
    return this.http.post<Ocorrencia>(`${this.apiUrl}/salvar`, ocorrencia);
  }

  atualizar(ocorrencia: Ocorrencia): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/atualizar`, ocorrencia);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${id}`);
  }

  despachar(id: string, veiculoId: string, equipeId?: string): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/despachar/${id}`, { veiculoId, equipeId: equipeId ?? null });
  }

  buscarPorMotorista(usuarioId: string): Observable<Ocorrencia[]> {
    return this.http.get<Ocorrencia[]>(`${this.apiUrl}/motorista/${usuarioId}`);
  }

  atualizarStatus(id: string, novoStatus: string): Observable<void> {
    let params = new HttpParams().set('novoStatus', novoStatus);
    return this.http.patch<void>(`${this.apiUrl}/${id}/status`, null, { params });
  }
}