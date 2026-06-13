import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
// ATENÇÃO: Ajuste este import para apontar para o model isolado de Funcionario que criamos,
// e avise seu colega para parar de exportar Funcionario de dentro de equipe.model.
import { Funcionario } from '../models/funcionario.model'; 

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  private apiUrl = 'http://localhost:8081/funcionario';

  constructor(private http: HttpClient) {}

  // MANTIDO PARA NÃO QUEBRAR A BRANCH 
  // '/equipe/{equipeId}' não existe no Controller atual. Vai dar 404.
  listarPorEquipe(equipeId: string): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(`${this.apiUrl}/equipe/${equipeId}`);
  }

  // Retirado o '<void>'. Agora o Angular extrai e reconhece o UUID!
  salvar(funcionario: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(`${this.apiUrl}/salvar`, funcionario);
  }

  atualizar(funcionario: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.apiUrl}/atualizar`, funcionario);
  }

  listarTodos(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(`${this.apiUrl}/listar`);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${id}`);
  }
}