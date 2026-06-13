import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TooltipModule } from 'primeng/tooltip';
import { SelectModule } from 'primeng/select';

import { Equipamento } from '../../core/models/equipamento.model';
import { Ambulancia } from '../../core/models/ambulancia.model';
import { RetiradaEstoqueDTO } from '../../core/models/retirada-estoque.dto';
import { EquipamentoService } from '../../core/services/equipamento.service';
import { AmbulanciaService } from '../../core/services/ambulancia.service';
import { EquipamentoFormComponent } from '../equipamento-form/equipamento-form.component';
@Component({
  selector: 'app-equipamento-lista',
  standalone: true,
  imports: [
    CommonModule, TableModule, ButtonModule, InputTextModule, FormsModule,
    ConfirmDialogModule, DialogModule, ToastModule, EquipamentoFormComponent,
    TooltipModule, SelectModule
  ],
  providers: [ConfirmationService],
  templateUrl: './equipamento-lista.component.html',
  styleUrl: './equipamento-lista.component.css' // Use o mesmo da lista de ambulâncias
})
export class EquipamentoListaComponent implements OnInit {
  equipamentos: Equipamento[] = [];
  ambulancias: Ambulancia[] = [];

  exibirDialogForm: boolean = false;
  idParaEditar: string | null = null;
  equipamentoParaEditar: Equipamento | null = null;

  exibirDialogDetalhes: boolean = false;
  equipamentoDetalhe: Equipamento | null = null;

  exibirDialogMovimentacao: boolean = false;
  tipoMovimentacao: 'ADICIONAR' | 'RETIRAR' = 'ADICIONAR';
  equipamentoMovimentacao: Equipamento | null = null;
  movimentacaoQuantidade: number | null = null;
  movimentacaoAmbulanciaId: string | null = null;

  constructor(
    private equipamentoService: EquipamentoService,
    private ambulanciaService: AmbulanciaService, // Injetado para popular o p-select
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.carregarEquipamentos();
    this.carregarAmbulancias();
  }

  carregarEquipamentos() {
    this.equipamentoService.listar().subscribe({
      next: (dados) => this.equipamentos = dados.filter(e => e.ativo !== false),
      error: () => this.mostrarErro('Falha ao carregar estoque.')
    });
  }

  carregarAmbulancias() {
    this.ambulanciaService.listar().subscribe({
      next: (dados) => this.ambulancias = dados.filter(a => a.ativo === true)
    });
  }

  novoEquipamento() {
    this.idParaEditar = null;
    this.equipamentoParaEditar = null;
    this.exibirDialogForm = true;
  }

  editarEquipamento(equipamento: Equipamento) {
    this.idParaEditar = equipamento.id || null;
    this.equipamentoParaEditar = equipamento;
    this.exibirDialogForm = true;
  }

  verDetalhes(equipamento: Equipamento) {
    this.equipamentoDetalhe = equipamento;
    this.exibirDialogDetalhes = true;
  }

  abrirMovimentacao(equipamento: Equipamento, tipo: 'ADICIONAR' | 'RETIRAR') {
    this.equipamentoMovimentacao = equipamento;
    this.tipoMovimentacao = tipo;
    this.movimentacaoQuantidade = null;
    this.movimentacaoAmbulanciaId = null;
    this.exibirDialogMovimentacao = true;
  }

  confirmarMovimentacao() {
    if (!this.equipamentoMovimentacao || !this.equipamentoMovimentacao.id || !this.movimentacaoQuantidade) return;

    if (this.tipoMovimentacao === 'ADICIONAR') {
      this.equipamentoService.colocarQuantidade(this.equipamentoMovimentacao.id, this.movimentacaoQuantidade).subscribe({
        next: () => this.finalizarMovimentacao('Estoque atualizado!'),
        error: () => this.mostrarErro('Falha ao adicionar quantidade.')
      });
    }
    else if (this.tipoMovimentacao === 'RETIRAR') {
      if (!this.movimentacaoAmbulanciaId) return;

      const dto: RetiradaEstoqueDTO = {
        quantidade: this.movimentacaoQuantidade,
        idAmbulancia: this.movimentacaoAmbulanciaId
      };

      this.equipamentoService.retirarQuantidade(this.equipamentoMovimentacao.id, dto).subscribe({
        next: () => this.finalizarMovimentacao('Equipamento transferido para a ambulância!'),
        error: (err) => {
          const msg = err.error?.message || 'Falha ao retirar. Verifique o saldo em estoque.';
          this.mostrarErro(msg);
        }
      });
    }
  }

  finalizarMovimentacao(mensagem: string) {
    this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: mensagem });
    this.exibirDialogMovimentacao = false;
    this.carregarEquipamentos(); // Recarrega a tabela para atualizar quantidades e histórico
  }

  confirmarExclusao(equipamento: Equipamento) {
    this.confirmationService.confirm({
      message: `Deseja realmente excluir ${equipamento.nome}?`,
      header: 'Confirmar Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        if (equipamento.id) {
          this.equipamentoService.excluir(equipamento.id).subscribe({
            next: () => {
              this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Item excluído.' });
              this.carregarEquipamentos();
            },
            error: () => this.mostrarErro('Não foi possível excluir.')
          });
        }
      }
    });
  }

  private mostrarErro(detalhe: string) {
    this.messageService.add({ severity: 'error', summary: 'Erro', detail: detalhe });
  }
}
