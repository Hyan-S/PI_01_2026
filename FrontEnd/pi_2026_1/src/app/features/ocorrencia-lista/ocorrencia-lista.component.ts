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
import { Ocorrencia } from '../../core/models/ocorrencia.model';
import { OcorrenciaService } from '../../core/services/ocorrencia.service';
import { OcorrenciaFormComponent } from '../ocorrencia-form/ocorrencia-form.component';

@Component({
  selector: 'app-ocorrencia-lista',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    InputTextModule,
    FormsModule,
    ConfirmDialogModule,
    DialogModule,
    ToastModule,
    OcorrenciaFormComponent,
  ],
  providers: [ConfirmationService],
  templateUrl: './ocorrencia-lista.component.html',
  styleUrl: './ocorrencia-lista.component.css',
})
export class OcorrenciaListaComponent implements OnInit {
  ocorrencias: Ocorrencia[] = [];

  exibirDialog: boolean = false;
  ocorrenciaParaEditar: Ocorrencia | null = null;

  exibirDialogDetalhes: boolean = false;
  ocorrenciaDetalhe: Ocorrencia | null = null;

  constructor(
    private ocorrenciaService: OcorrenciaService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
  ) {}

  ngOnInit(): void {
    this.carregarOcorrencias();
  }

  carregarOcorrencias() {
    this.ocorrenciaService.listar().subscribe({
      next: (dados) => {
        this.ocorrencias = dados.filter((o) => o.status !== 'CANCELADA');
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Falha ao carregar as ocorrências.',
        });
      },
    });
  }

  novaOcorrencia() {
    this.ocorrenciaParaEditar = null;
    this.exibirDialog = true;
  }

  editar(ocorrencia: Ocorrencia) {
    this.ocorrenciaParaEditar = ocorrencia;
    this.exibirDialog = true;
  }

  detalhes(ocorrencia: Ocorrencia) {
    this.ocorrenciaDetalhe = ocorrencia;
    this.exibirDialogDetalhes = true;
  }

  fecharModalForm(visivel: boolean) {
    this.exibirDialog = visivel;
    if (!visivel) {
      this.ocorrenciaParaEditar = null;
    }
  }

  confirmarCancelamento(ocorrencia: Ocorrencia) {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja cancelar a ocorrência "${ocorrencia.protocolo}"?`,
      header: 'Confirmação de Cancelamento',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        if (ocorrencia.id) {
          this.ocorrenciaService.excluir(ocorrencia.id).subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Sucesso',
                detail: 'Ocorrência cancelada.',
              });
              this.carregarOcorrencias();
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Erro',
                detail: 'Falha ao cancelar ocorrência.',
              });
            },
          });
        }
      },
    });
  }

  obterClasseGravidade(gravidade?: string): string {
    if (!gravidade) return 'gravidade-sem';
    return `gravidade-${gravidade.toLowerCase()}`;
  }

  obterClasseStatus(status?: string): string {
    if (!status) return 'status-sem';
    return `status-${status.toLowerCase().replace('_', '-')}`;
  }
}
