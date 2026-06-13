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
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { TextareaModule } from 'primeng/textarea';
import { AmbulanciaService } from '../../core/services/ambulancia.service';
import { Ambulancia } from '../../core/models/ambulancia.model';

@Component({
  selector: 'app-ambulancia-form',
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
  templateUrl: './ambulancia-form.component.html',
  styleUrl: './ambulancia-form.component.css',
})
export class AmbulanciaFormComponent implements OnInit, OnChanges {
  @Input() visible: boolean = false;
  @Input() ambulanciaId: string | null = null;
  @Input() modoSomenteLeitura: boolean = false;

  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() salvo = new EventEmitter<void>();

  titulo: string = 'Nova Ambulância';
  formAmbulancia!: FormGroup;

  opcoesStatus = [
    { label: 'Disponível', value: 'DISPONIVEL' },
    { label: 'A Caminho', value: 'A_CAMINHO' },
    { label: 'Em Atendimento', value: 'EM_ATENDIMENTO' },
    { label: 'Em Manutenção', value: 'EM_MANUTENCAO' },
  ];

  constructor(
    private fb: FormBuilder,
    private ambulanciaService: AmbulanciaService,
    private messageService: MessageService,
  ) {}

  ngOnInit() {
    this.iniciarFormulario();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (!this.formAmbulancia) return;

    if (changes['ambulanciaId']) {
      this.titulo = this.ambulanciaId ? 'Editar Ambulância' : 'Nova Ambulância';

      if (this.ambulanciaId) {
        this.carregarDadosAmbulancia(this.ambulanciaId);

      } else {
        this.formAmbulancia.reset({
          id: null,
          descricao: '',
          placa: '',
          observacao: '',
          status: 'DISPONIVEL',
        });
      }
      this.formAmbulancia.get('status')?.disable();
    }

    if (changes['modoSomenteLeitura']) {
      if (this.modoSomenteLeitura) {
        this.formAmbulancia.disable();
      } else {
        this.formAmbulancia.enable();
        this.formAmbulancia.get('status')?.disable();
      }
    }
  }
  iniciarFormulario() {
    this.formAmbulancia = this.fb.group({
      id: [null],
      descricao: ['', Validators.required],
      placa: ['', Validators.required],
      observacao: [''],
      status: [{ value: 'DISPONIVEL', disabled: true }, Validators.required],
    });
  }

  carregarDadosAmbulancia(id: string) {
    this.ambulanciaService.buscarPorId(id).subscribe({
      next: (ambulancia) => {
        this.formAmbulancia.patchValue(ambulancia);
      },
    });
  }

  salvar() {
    if (this.formAmbulancia.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atenção',
        detail: 'Preencha os campos obrigatórios.',
      });
      return;
    }

    const ambulancia: Ambulancia = this.formAmbulancia.getRawValue();

    this.ambulanciaService.listar().subscribe({
      next: (listaAmbulancias) => {

        const placaDuplicada = listaAmbulancias.find(
          (a) =>
            a.placa.trim().toUpperCase() === ambulancia.placa.trim().toUpperCase() &&
            a.id !== ambulancia.id,
        );

        if (placaDuplicada) {
          this.messageService.add({
            severity: 'error',
            summary: 'Placa Inválida',
            detail: `A placa ${ambulancia.placa} já está cadastrada no sistema!`,
          });
          return;
        }

        const request = this.ambulanciaId
          ? this.ambulanciaService.atualizar(ambulancia)
          : this.ambulanciaService.salvar(ambulancia);

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
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível validar a placa. Verifique sua conexão.',
        });
      },
    });
  }
  fechar() {
    this.ambulanciaId = null;
    this.formAmbulancia.reset({
      id: null,
      descricao: '',
      placa: '',
      observacao: '',
      status: 'DISPONIVEL',
    });
    this.formAmbulancia.get('status')?.disable();
    this.visibleChange.emit(false);
  }
}
