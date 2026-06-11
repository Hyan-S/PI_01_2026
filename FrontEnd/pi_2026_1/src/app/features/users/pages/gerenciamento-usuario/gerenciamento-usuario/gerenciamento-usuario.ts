import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

// Imports PrimeNG 18+
import { Table, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { UsuarioService } from '../../../../../core/services/usuario.service';


export interface Usuario {
  id: string;
  nome: string;
  cpf: string;
  email: string;
  nivelAcesso: string;
}

@Component({
  selector: 'app-gerenciamento-usuario',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    TableModule, 
    ButtonModule, 
    InputTextModule, 
    SelectModule,
    TagModule,
    ConfirmDialogModule,
    ToastModule          // 💡 Módulo dos Toasts de Feedback
  ],
  providers: [ConfirmationService, MessageService], // 💡 Provedores de Serviço Injetados
  templateUrl: './gerenciamento-usuario.html',
  styleUrl: './gerenciamento-usuario.css',
})
export class GerenciamentoUsuarioComponent implements OnInit {
  @ViewChild('dt') dt!: Table;

  usuarios: Usuario[] = [];
  carregando: boolean = true;

  niveisAcesso = [
    { label: 'Todos', value: null },
    { label: 'Administrador', value: 'ADMIN' },
    { label: 'Regulador', value: 'REGULADOR' },
    { label: 'Motorista', value: 'MOTORISTA' },
    { label: 'Solicitante', value: 'SOLICITANTE' }
  ];

  constructor(
    private router: Router,
    private usuarioService: UsuarioService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  carregarUsuarios() {
    this.carregando = true;
    this.usuarioService.listarTodos().subscribe({
      next: (dados) => {
        this.usuarios = dados;
        this.carregando = false;
      },
      error: (erro) => {
        console.error('Erro ao carregar usuários:', erro);
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao buscar usuários no servidor.' });
        this.carregando = false;
      }
    });
  }

  filtrarGlobal(event: Event) {
    const input = event.target as HTMLInputElement;
    this.dt.filterGlobal(input.value, 'contains');
  }

  novoUsuario() {
    this.router.navigate(['/usuarios/cadastro']);
  }

  editarUsuario(id: string) {
    this.router.navigate(['/usuarios/editar', id]);
  }

  excluirUsuario(usuario: Usuario) {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja inativar o acesso de <b>${usuario.nome}</b>?`,
      header: 'Confirmar Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Inativar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.carregando = true;
        this.usuarioService.excluir(usuario.id).subscribe({
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Usuário inativado com sucesso.' });
            this.carregarUsuarios(); // Recarrega a tabela após excluir
          },
          error: (erro) => {
            console.error('Erro ao inativar:', erro);
            this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível inativar o usuário.' });
            this.carregando = false;
          }
        });
      }
    });
  }

  getSeveridadeAcesso(nivel: string): 'success' | 'info' | 'warn' | 'danger' | 'secondary' {
    switch (nivel) {
      case 'ADMIN': return 'danger';
      case 'REGULADOR': return 'warn';
      case 'MOTORISTA': return 'info';
      case 'SOLICITANTE': return 'success';
      default: return 'secondary';
    }
  }
}