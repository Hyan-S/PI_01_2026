import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { SelectModule } from 'primeng/select';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Ocorrencia } from '../../core/models/ocorrencia.model';
import { Ambulancia } from '../../core/models/ambulancia.model';
import { Equipe } from '../../core/models/equipe.model';
import { OcorrenciaService } from '../../core/services/ocorrencia.service';
import { AmbulanciaService } from '../../core/services/ambulancia.service';
import { EquipeService } from '../../core/services/equipe.service';
import { OcorrenciaFormComponent } from '../ocorrencia-form/ocorrencia-form.component';
import { DividerModule } from 'primeng/divider';
import { AuthService } from '../../core/services/auth.service';

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
    SelectModule,
    DividerModule,
    OcorrenciaFormComponent,
  ],
  providers: [ConfirmationService],
  templateUrl: './ocorrencia-lista.component.html',
  styleUrl: './ocorrencia-lista.component.css',
})
export class OcorrenciaListaComponent implements OnInit {
  ocorrencias: Ocorrencia[] = [];

  isOperacional: boolean = false;
  ocorrenciaAtivaMotorista: Ocorrencia | null = null;
  usuarioLogadoId: string | null = null;

  exibirDialog: boolean = false;
  ocorrenciaParaEditar: Ocorrencia | null = null;

  exibirDialogDetalhes: boolean = false;
  ocorrenciaDetalhe: Ocorrencia | null = null;

  exibirDialogDespacho: boolean = false;
  ocorrenciaParaDespachar: Ocorrencia | null = null;
  ambulanciasSelecionada: string | null = null;
  equipeSelecionada: string | null = null;
  ambulancias: Ambulancia[] = [];
  equipes: Equipe[] = [];
  ambulanciasFiltradas: Ambulancia[] = [];
  equipesFiltradas: Equipe[] = [];

  constructor(
    private ocorrenciaService: OcorrenciaService,
    private ambulanciaService: AmbulanciaService,
    private equipeService: EquipeService,
    private authService: AuthService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
  ) {}

ngOnInit(): void {
    const usuarioInfo = this.authService.getUsuarioSessao();
    this.usuarioLogadoId = usuarioInfo?.id || null;
    const nivel = usuarioInfo?.nivelAcesso;

    this.isOperacional = nivel === 'MOTORISTA' || nivel === 'MEDICO' || nivel === 'ENFERMEIRO';

    if (this.isOperacional) {
      this.carregarOcorrenciaMotorista();
    } else {
      this.carregarOcorrencias();
      this.carregarAmbulanciasFiltros();
      this.carregarEquipes();
    }
  }

  carregarOcorrenciaMotorista() {
    if (!this.usuarioLogadoId) return;

    this.ocorrenciaService.buscarPorMotorista(this.usuarioLogadoId).subscribe({
      next: (dados) => {
        this.ocorrenciaAtivaMotorista = dados.length > 0 ? dados[0] : null;
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao buscar sua viagem.' })
    });
  }

  atualizarStatusOcorrencia(novoStatus: string) {
    if (!this.ocorrenciaAtivaMotorista?.id) return;

    this.ocorrenciaService.atualizarStatus(this.ocorrenciaAtivaMotorista.id, novoStatus).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Atualizado', detail: 'Status da viagem atualizado.' });
        this.carregarOcorrenciaMotorista(); // Recarrega para ver se a viagem sumiu (ENCERRADA)
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.message || 'Falha ao atualizar status.' });
      }
    });
  }

  carregarOcorrencias() {
    this.ocorrenciaService.listar().subscribe({
      next: (dados) => {
        this.ocorrencias = dados.filter((o) => o.status !== 'CANCELADA');
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao carregar as ocorrências.' });
      },
    });
  }

  carregarAmbulanciasFiltros() {
    this.ambulanciaService.listar().subscribe({
      next: (lista) => {
        this.ambulancias = lista.filter((a) => a.status === 'DISPONIVEL');
        this.ambulanciasFiltradas = [...this.ambulancias];
      },
    });
  }

  carregarEquipes() {
    this.equipeService.listar().subscribe({
      next: (lista) => {
        this.equipes = lista.filter((e) => !e.ambulancia || e.ambulancia?.status === 'DISPONIVEL');
        this.equipesFiltradas = [...this.equipes];
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao carregar equipes elegíveis.' });
      }
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

  abrirDespacho(ocorrencia: Ocorrencia) {
    this.ocorrenciaParaDespachar = ocorrencia;
    this.ambulanciasSelecionada = null;
    this.equipeSelecionada = null;
    this.ambulanciasFiltradas = [...this.ambulancias];
    this.equipesFiltradas = [...this.equipes];
    this.carregarAmbulanciasFiltros();
    this.exibirDialogDespacho = true;
  }

  onAmbulanciaDespachoChange(ambulanciaId: string | null) {
    if (!ambulanciaId) {
      this.equipesFiltradas = [...this.equipes];
      this.equipeSelecionada = null;
      return;
    }
    const equipeVinculada = this.equipes.find((e) => e.ambulancia?.id === ambulanciaId);
    if (equipeVinculada) {
      this.equipesFiltradas = [equipeVinculada];
      this.equipeSelecionada = equipeVinculada.id!;
    } else {
      this.equipesFiltradas = [...this.equipes];
      this.equipeSelecionada = null;
    }
  }

  onEquipeDespachoChange(equipeId: string | null) {
    if (!equipeId) {
      this.ambulanciasFiltradas = [...this.ambulancias];
      this.ambulanciasSelecionada = null;
      return;
    }
    const equipe = this.equipes.find((e) => e.id === equipeId);
    const ambId = equipe?.ambulancia?.id;
    const ambVinculada = ambId ? this.ambulancias.find((a) => a.id === ambId) : null;
    if (ambVinculada) {
      this.ambulanciasFiltradas = [ambVinculada];
      this.ambulanciasSelecionada = ambVinculada.id!;
    } else {
      this.ambulanciasFiltradas = [...this.ambulancias];
    }
  }

  confirmarDespacho() {
    if (!this.ocorrenciaParaDespachar?.id || !this.ambulanciasSelecionada) return;

    this.ocorrenciaService
      .despachar(this.ocorrenciaParaDespachar.id, this.ambulanciasSelecionada, this.equipeSelecionada ?? undefined)
      .subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Despachado', detail: 'Ambulância despachada com sucesso!' });
          this.exibirDialogDespacho = false;
          this.carregarOcorrencias();
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao despachar.' });
        },
      });
  }

  onOcorrenciaSalva(criada: Ocorrencia | null) {
    this.carregarOcorrencias();
    if (criada) {
      this.abrirDespacho(criada);
    }
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
              this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Ocorrência cancelada.' });
              this.carregarOcorrencias();
            },
            error: () => {
              this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao cancelar ocorrência.' });
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
