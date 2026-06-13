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
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { MessageService } from 'primeng/api';
import { Ocorrencia } from '../../core/models/ocorrencia.model';
import { OcorrenciaService } from '../../core/services/ocorrencia.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-ocorrencia-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    TextareaModule,
    SelectModule,
    ButtonModule,
    DialogModule,
  ],
  templateUrl: './ocorrencia-form.component.html',
  styleUrl: './ocorrencia-form.component.css',
})
export class OcorrenciaFormComponent implements OnInit, OnChanges {
  @Input() visible: boolean = false;
  @Input() ocorrencia: Ocorrencia | null = null;

  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() salvo = new EventEmitter<void>();

  titulo: string = 'Nova Ocorrência';
  formOcorrencia!: FormGroup;

  opcoesGravidade = [
    { label: 'Baixo', value: 'BAIXO' },
    { label: 'Médio', value: 'MEDIO' },
    { label: 'Alto', value: 'ALTO' },
    { label: 'Crítico', value: 'CRITICO' },
  ];

  opcoesTipo = [
    { label: 'Acidente de Trânsito', value: 'Acidente de Trânsito' },
    { label: 'Incêndio', value: 'Incêndio' },
    { label: 'Emergência Médica', value: 'Emergência Médica' },
    { label: 'Desastre Natural', value: 'Desastre Natural' },
    { label: 'Outro', value: 'Outro' },
  ];

  constructor(
    private fb: FormBuilder,
    private ocorrenciaService: OcorrenciaService,
    private authService: AuthService,
    private messageService: MessageService,
  ) {}

  ngOnInit() {
    this.iniciarFormulario();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (!this.formOcorrencia) return;

    if (changes['ocorrencia']) {
      if (this.ocorrencia) {
        this.titulo = 'Editar Ocorrência';
        this.formOcorrencia.patchValue(this.ocorrencia);
      } else {
        this.titulo = 'Nova Ocorrência';
        this.formOcorrencia.reset({ gravidade: 'MEDIO', tipo: 'Emergência Médica' });
      }
    }
  }

  iniciarFormulario() {
    this.formOcorrencia = this.fb.group({
      id: [null],
      titulo: ['', Validators.required],
      descricao: ['', Validators.required],
      tipo: ['Emergência Médica', Validators.required],
      gravidade: ['MEDIO', Validators.required],
      endereco: ['', Validators.required],
      status: [null],
      protocolo: [null],
      operadorId: [null],
      equipeId: [null],
    });
  }

  salvar() {
    if (this.formOcorrencia.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atenção',
        detail: 'Preencha os campos obrigatórios.',
      });
      return;
    }

    const dados = this.formOcorrencia.getRawValue();

    if (!dados.operadorId) {
      dados.operadorId = this.authService.getUsuarioSessao()?.id;
    }

    const request = this.ocorrencia
      ? this.ocorrenciaService.atualizar(dados)
      : this.ocorrenciaService.salvar(dados);

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
    this.formOcorrencia.reset({ gravidade: 'MEDIO', tipo: 'Emergência Médica' });
    this.visibleChange.emit(false);
  }
}
