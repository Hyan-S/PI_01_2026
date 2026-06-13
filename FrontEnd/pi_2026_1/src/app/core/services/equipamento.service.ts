import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipamento } from '../models/equipamento.model';
import { RetiradaEstoqueDTO } from '../models/retirada-estoque.dto';

@Injectable({
  providedIn: 'root'
})
export class EquipamentoService {
  private apiUrl = 'http://localhost:8081/equipamento';

  constructor(private http: HttpClient) {}

  listar(): Observable<Equipamento[]> {
    return this.http.get<Equipamento[]>(`${this.apiUrl}/listar`);
  }

  buscarPorNome(nome: string): Observable<Equipamento[]> {
    return this.http.get<Equipamento[]>(`${this.apiUrl}/nome/${nome}`);
  }

  salvar(equipamento: Equipamento): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/salvar`, equipamento);
  }

  atualizar(equipamento: Equipamento): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/atualizar`, equipamento);
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${id}`);
  }

  colocarQuantidade(id: string, quantidade: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/colocar/${id}`, quantidade);
  }

  retirarQuantidade(id: string, retiradaDTO: RetiradaEstoqueDTO): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/retirar/${id}`, retiradaDTO);
  }
}
