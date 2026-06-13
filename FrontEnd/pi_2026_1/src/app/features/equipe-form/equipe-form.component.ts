import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { MessageService } from 'primeng/api';
import { Equipe } from '../../core/models/equipe.model';
import { EquipeService } from '../../core/services/equipe.service';

@Component({
  selector: 'app-equipe-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, InputTextModule, ButtonModule, DialogModule],
  templateUrl: './equipe-form.component.html',
  styleUrl: './equipe-form.component.css',
})
export class EquipeFormComponent implements OnInit, OnChanges {
  @Input() visible: boolean = false;
  @Input() equipeId: string | null = null;

  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() salvo = new EventEmitter<void>();

  titulo: string = 'Nova Equipe';
  formEquipe!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private equipeService: EquipeService,
    private messageService: MessageService,
  ) {}

  ngOnInit() {
    this.iniciarFormulario();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (!this.formEquipe) return;

    if (changes['equipeId']) {
      this.titulo = this.equipeId ? 'Editar Equipe' : 'Nova Equipe';

      if (this.equipeId) {
        this.carregarDados(this.equipeId);
      } else {
        this.formEquipe.reset({ id: null, nomeEquipe: '', identificador: '' });
      }
    }
  }

  iniciarFormulario() {
    this.formEquipe = this.fb.group({
      id: [null],
      nomeEquipe: ['', Validators.required],
      identificador: ['', Validators.required],
    });
  }

  carregarDados(id: string) {
    this.equipeService.buscarPorId(id).subscribe({
      next: (equipe) => {
        this.formEquipe.patchValue(equipe);
      },
    });
  }

  salvar() {
    if (this.formEquipe.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atenção',
        detail: 'Preencha os campos obrigatórios.',
      });
      return;
    }

    const equipe: Equipe = this.formEquipe.getRawValue();
    const request = this.equipeId
      ? this.equipeService.atualizar(equipe)
      : this.equipeService.salvar(equipe);

    request.subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Operação realizada com sucesso!',
        });
        this.salvo.emit();
        this.fechar();
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Ocorreu uma falha na operação com o banco de dados.',
        });
      },
    });
  }

  fechar() {
    this.equipeId = null;
    this.formEquipe.reset({ id: null, nomeEquipe: '', identificador: '' });
    this.visibleChange.emit(false);
  }
}
