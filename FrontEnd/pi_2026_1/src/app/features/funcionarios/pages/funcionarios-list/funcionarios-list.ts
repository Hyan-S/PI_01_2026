import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'; // 💡 Adicionado ReactiveFormsModule

// Imports PrimeNG
import { Table, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { TagModule } from 'primeng/tag';
import { DialogModule } from 'primeng/dialog'; // 💡 Import do Modal
import { InputNumberModule } from 'primeng/inputnumber'; // 💡 Import para forçar números
import { ConfirmationService, MessageService } from 'primeng/api';
import { Funcionario } from '../../../../core/models/funcionario.model';
import { FuncionarioService } from '../../../../core/services/funcionario.service';

@Component({
  selector: 'app-funcionario-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule, // 💡 Adicionado aqui
    TableModule,
    ButtonModule,
    InputTextModule,
    SelectModule,
    ConfirmDialogModule,
    ToastModule,
    TagModule,
    DialogModule, // 💡 Adicionado aqui
    InputNumberModule // 💡 Adicionado aqui
  ],
  providers: [ConfirmationService, MessageService],
  templateUrl: './funcionarios-list.html',
  styleUrls: ['./funcionarios-list.css']
})
export class FuncionarioListComponent implements OnInit {
@ViewChild('dt') dt!: Table;

  funcionarios: Funcionario[] = [];
  carregando: boolean = true;
  
  // Variáveis do Modal e Formulário
  exibirDialogo: boolean = false;
  funcionarioForm!: FormGroup;

  funcoesFiltro = [
    { label: 'Todas as Funções', value: null },
    { label: 'Médico', value: 'MEDICO' },
    { label: 'Enfermeiro', value: 'ENFERMEIRO' },
    { label: 'Motorista', value: 'MOTORISTA' }
  ];

  funcoesOpcoes = [
    { label: 'Médico', value: 'MEDICO' },
    { label: 'Enfermeiro', value: 'ENFERMEIRO' },
    { label: 'Motorista', value: 'MOTORISTA' }
  ];

  constructor(
    private funcionarioService: FuncionarioService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private fb: FormBuilder 
  ) {}

  ngOnInit(): void {
    this.iniciarFormulario();
    this.carregarFuncionarios();
  }

  iniciarFormulario() {
    this.funcionarioForm = this.fb.group({
      // 💡 Validadores aplicados na raiz do form
      id: [null], // 💡 Campo oculto vital para diferenciar Cadastro de Edição
      funcao: [null, Validators.required],
      descricao: ['', [Validators.required, Validators.minLength(3)]],
      numeroFuncao: [null, [Validators.required, Validators.min(1)]]
    });
  }

  carregarFuncionarios() {
    this.carregando = true;
    this.funcionarioService.listarTodos().subscribe({
      next: (dados) => {
        this.funcionarios = dados;
        this.carregando = false;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao buscar funcionários.' });
        this.carregando = false;
      }
    });
  }

  filtrarGlobal(event: Event) {
    const input = event.target as HTMLInputElement;
    this.dt.filterGlobal(input.value, 'contains');
  }

  abrirDialogoNovo() {
    this.funcionarioForm.reset();
    this.exibirDialogo = true;
  }

  esconderDialogo() {
    this.exibirDialogo = false;
  }

// 💡 MÉTODO SALVAR REFATORADO (Faz POST ou PUT)
  salvarFuncionario() {
    if (this.funcionarioForm.invalid) return;

    // Extrai os dados do formulário
    const dadosFormulario: Funcionario = this.funcionarioForm.value;

    this.carregando = true;

    // Se tem ID, é EDIÇÃO. Se não tem, é CADASTRO NOVO.
    if (dadosFormulario.id) {
      this.funcionarioService.atualizar(dadosFormulario).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Funcionário atualizado!' });
          this.finalizarAcao();
        },
        error: () => this.tratarErroAcao('atualizar')
      });
    } else {
      this.funcionarioService.salvar(dadosFormulario).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Funcionário cadastrado!' });
          this.finalizarAcao();
        },
        error: () => this.tratarErroAcao('salvar')
      });
    }
  }

  // 💡 NOVO MÉTODO DE EDIÇÃO
  editarFuncionario(id: string) {
    // Busca o funcionário na memória (já carregado na tabela)
    const funcionario = this.funcionarios.find(f => f.id === id);
    
    if (funcionario) {
      // patchValue preenche os campos do formulário automaticamente usando as chaves do objeto
      this.funcionarioForm.patchValue(funcionario); 
      this.exibirDialogo = true;
    } else {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Funcionário não encontrado na memória.' });
    }
  }

  excluirFuncionario(funcionario: Funcionario) {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja excluir o funcionário <b>${funcionario.descricao}</b>?`,
      header: 'Confirmar Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Excluir',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.carregando = true;
        // O ID é garantido pela tipagem da interface
        this.funcionarioService.excluir(funcionario.id!).subscribe({
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Funcionário excluído com sucesso.' });
            this.carregarFuncionarios();
          },
          error: (erro) => {
            console.error('Erro ao excluir:', erro);
            this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível excluir o funcionário.' });
            this.carregando = false;
          }
        });
      }
    });
  }

  getSeveridadeFuncao(funcao: string): 'success' | 'info' | 'warn' | 'danger' | 'secondary' | 'contrast' {
    switch (funcao?.toUpperCase()) {
      case 'MEDICO': return 'danger'; // Vermelho para médicos (Urgência)
      case 'ENFERMEIRO': return 'warn'; // Laranja para enfermeiros
      case 'MOTORISTA': return 'info';  // Azul para motoristas
      default: return 'secondary';
    }
  }

  // Métodos auxiliares
  private finalizarAcao() {
    this.exibirDialogo = false;
    this.carregarFuncionarios(); // Recarrega a tabela para mostrar dados novos
  }

  private tratarErroAcao(acao: string) {
    this.messageService.add({ severity: 'error', summary: 'Erro', detail: `Não foi possível ${acao} o funcionário.` });
    this.carregando = false;
  }
}