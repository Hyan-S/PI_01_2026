import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Funcionario } from '../models/equipe.model';

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  private apiUrl = 'http://localhost:8081/funcionario';

  constructor(private http: HttpClient) {}

  listarPorEquipe(equipeId: string): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(`${this.apiUrl}/equipe/${equipeId}`);
  }

  salvar(funcionario: Funcionario): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/salvar`, funcionario);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${id}`);
  }
}
