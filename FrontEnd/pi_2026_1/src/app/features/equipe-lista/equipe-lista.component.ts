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
import { Equipe } from '../../core/models/equipe.model';
import { EquipeService } from '../../core/services/equipe.service';
import { EquipeFormComponent } from '../equipe-form/equipe-form.component';

@Component({
  selector: 'app-equipe-lista',
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
    EquipeFormComponent,
  ],
  providers: [ConfirmationService],
  templateUrl: './equipe-lista.component.html',
  styleUrl: './equipe-lista.component.css',
})
export class EquipeListaComponent implements OnInit {
  equipes: Equipe[] = [];

  exibirDialog: boolean = false;
  idParaEditar: string | null = null;

  exibirDialogDetalhes: boolean = false;
  equipeDetalhe: Equipe | null = null;

  constructor(
    private equipeService: EquipeService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
  ) {}

  ngOnInit(): void {
    this.carregarEquipes();
  }

  carregarEquipes() {
    this.equipeService.listar().subscribe({
      next: (dados) => {
        this.equipes = dados;
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Falha ao carregar as equipes.',
        });
      },
    });
  }

  novaEquipe() {
    this.idParaEditar = null;
    this.exibirDialog = true;
  }

  editar(id: string) {
    this.idParaEditar = id;
    this.exibirDialog = true;
  }

  detalhes(equipe: Equipe) {
    this.equipeDetalhe = equipe;
    this.exibirDialogDetalhes = true;
  }

  fecharModalForm(visivel: boolean) {
    this.exibirDialog = visivel;
    if (!visivel) {
      this.idParaEditar = null;
    }
  }

  confirmarExclusao(equipe: Equipe) {
    this.confirmationService.confirm({
      message: `Tem certeza que deseja excluir a equipe "${equipe.nomeEquipe}"?`,
      header: 'Confirmação de Exclusão',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sim',
      rejectLabel: 'Não',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        if (equipe.id) {
          this.equipeService.excluir(equipe.id).subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Sucesso',
                detail: 'Equipe excluída.',
              });
              this.equipes = this.equipes.filter((e) => e.id !== equipe.id);
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Erro',
                detail: 'Falha ao excluir equipe.',
              });
            },
          });
        }
      },
    });
  }
}
