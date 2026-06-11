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
  ],
  providers: [ConfirmationService],
  templateUrl: './ambulancia-lista.component.html',
  styleUrl: './ambulancia-lista.component.css',
})
export class AmbulanciaListaComponent implements OnInit {
  ambulancias: Ambulancia[] = [];
  termoBusca: string = '';

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
        this.ambulancias = dados;
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

  novaAmbulancia() {
    this.router.navigate(['/ambulancias/novo']);
  }

  editar(id: string) {
    this.router.navigate(['/ambulancias/editar', id]);
  }

  detalhes(id: string) {
    this.router.navigate(['/ambulancias/detalhes', id]);
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
              this.carregarAmbulancias();
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
}

