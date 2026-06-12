import { Component, OnInit } from '@angular/core';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Ambulancia } from '../../core/models/ambulancia.model';
import { AmbulanciaService } from '../../core/services/ambulancia.service';
import { Router } from '@angular/router';
import { AmbulanciaFormComponent } from '../ambulancia-form/ambulancia-form.component';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-ambulancia-lista',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    InputTextModule,
    ConfirmDialogModule,
    FormsModule,
    AmbulanciaFormComponent,
    DialogModule,
    ToastModule,
  ],
  providers: [ConfirmationService],
  templateUrl: './ambulancia-lista.component.html',
  styleUrl: './ambulancia-lista.component.css',
})
export class AmbulanciaListaComponent implements OnInit {
  ambulancias: Ambulancia[] = [];
  termoBusca: string = '';

  exibirDialogDetalhes: boolean = false;
  ambulanciaDetalhe: Ambulancia | null = null;

  constructor(
    private ambulanciaService: AmbulanciaService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
  ) {}

  ngOnInit(): void {
    this.carregarAmbulancias();
  }

  carregarAmbulancias() {
    this.ambulanciaService.listar().subscribe({
      next: (dados) => {
        this.ambulancias = dados.filter((amb) => amb.ativo === true);

      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Falha ao carregar as ambulâncias.',
        });
      },
    });
  }

  detalhes(id: string) {
    this.ambulanciaService.buscarPorId(id).subscribe({
      next: (dados) => {
        this.ambulanciaDetalhe = dados;
        this.exibirDialogDetalhes = true;
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Falha ao carregar os detalhes da ambulância.',
        });
      },
    });
  }
  confirmarExclusao(ambulancia: Ambulancia) {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja excluir a ambulância placa ${ambulancia.placa}?`,
      header: 'Confirmação de Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        if (ambulancia.id) {
          this.ambulanciaService.excluir(ambulancia.id).subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Sucesso',
                detail: 'Ambulância excluída.',
              });

              this.ambulancias = this.ambulancias.filter((a) => a.id !== ambulancia.id);
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Erro',
                detail: 'Falha ao excluir ambulância.',
              });
            },
          });
        }
      },
    });
  }
  exibirDialog: boolean = false;
  idParaEditar: string | null = null;

  novaAmbulancia() {
    this.idParaEditar = null;
    this.exibirDialog = true;
  }

  editar(id: string) {
    this.idParaEditar = id;
    this.exibirDialog = true;
  }
  fecharModalForm(visivel: boolean) {
    this.exibirDialog = visivel;
    if (!visivel) {
      this.idParaEditar = null;
    }
  }

  obterClasseStatus(status?: string): string {
    if (!status) return 'status-sem-status';

    const statusFormatado = status
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/[\s_]+/g, '-');

    return `status-${statusFormatado}`;
  }
}
