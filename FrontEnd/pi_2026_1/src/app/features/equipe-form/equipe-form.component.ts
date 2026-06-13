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
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { MessageService } from 'primeng/api';
import { Equipe, Funcionario } from '../../core/models/equipe.model';
import { Ambulancia } from '../../core/models/ambulancia.model';
import { EquipeService } from '../../core/services/equipe.service';
import { AmbulanciaService } from '../../core/services/ambulancia.service';
import { FuncionarioService } from '../../core/services/funcionario.service';

@Component({
  selector: 'app-equipe-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    InputNumberModule,
    SelectModule,
    ButtonModule,
    DialogModule,
    DividerModule,
  ],
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
  formMembro!: FormGroup;

  ambulancias: Ambulancia[] = [];
  funcionarios: Funcionario[] = [];
  adicionandoMembro: boolean = false;

  constructor(
    private fb: FormBuilder,
    private equipeService: EquipeService,
    private ambulanciaService: AmbulanciaService,
    private funcionarioService: FuncionarioService,
    private messageService: MessageService,
  ) {}

  ngOnInit() {
    this.iniciarFormularios();
    this.carregarAmbulanciasDiponiveis();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (!this.formEquipe) return;

    if (changes['equipeId']) {
      this.titulo = this.equipeId ? 'Editar Equipe' : 'Nova Equipe';
      this.adicionandoMembro = false;
      this.funcionarios = [];

      if (this.equipeId) {
        this.carregarDados(this.equipeId);
        this.carregarFuncionarios(this.equipeId);
      } else {
        this.formEquipe.reset({ id: null, nomeEquipe: '', identificador: '', ambulanciaId: null });
      }
    }
  }

  iniciarFormularios() {
    this.formEquipe = this.fb.group({
      id: [null],
      nomeEquipe: ['', Validators.required],
      identificador: ['', Validators.required],
      ambulanciaId: [null],
    });

    this.formMembro = this.fb.group({
      funcao: ['', Validators.required],
      descricao: [''],
      numeroFuncao: [0],
    });
  }

  carregarAmbulanciasDiponiveis() {
    this.ambulanciaService.listar().subscribe({
      next: (lista) => (this.ambulancias = lista),
    });
  }

  carregarDados(id: string) {
    this.equipeService.buscarPorId(id).subscribe({
      next: (equipe) => {
        this.formEquipe.patchValue({
          id: equipe.id,
          nomeEquipe: equipe.nomeEquipe,
          identificador: equipe.identificador,
          ambulanciaId: equipe.ambulancia?.id ?? null,
        });
      },
    });
  }

  carregarFuncionarios(equipeId: string) {
    this.funcionarioService.listarPorEquipe(equipeId).subscribe({
      next: (lista) => (this.funcionarios = lista),
    });
  }

  salvar() {
    if (this.formEquipe.invalid) {
      this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha os campos obrigatórios.' });
      return;
    }

    const formValue = this.formEquipe.getRawValue();
    const equipe: Equipe = {
      id: formValue.id,
      nomeEquipe: formValue.nomeEquipe,
      identificador: formValue.identificador,
      ambulancia: formValue.ambulanciaId ? { id: formValue.ambulanciaId } : undefined,
    };

    const request = this.equipeId
      ? this.equipeService.atualizar(equipe)
      : this.equipeService.salvar(equipe);

    request.subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Operação realizada com sucesso!' });
        this.salvo.emit();
        this.fechar();
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Ocorreu uma falha na operação com o banco de dados.' });
      },
    });
  }

  adicionarMembro() {
    if (this.formMembro.invalid || !this.equipeId) return;

    const funcionario: Funcionario = {
      ...this.formMembro.getRawValue(),
      equipe: { id: this.equipeId },
    };

    this.funcionarioService.salvar(funcionario).subscribe({
      next: () => {
        this.formMembro.reset({ funcao: '', descricao: '', numeroFuncao: 0 });
        this.adicionandoMembro = false;
        this.carregarFuncionarios(this.equipeId!);
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Membro adicionado.' });
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao adicionar membro.' });
      },
    });
  }

  removerMembro(funcionario: Funcionario) {
    if (!funcionario.id) return;
    this.funcionarioService.excluir(funcionario.id).subscribe({
      next: () => {
        this.carregarFuncionarios(this.equipeId!);
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Membro removido.' });
      },
    });
  }

  fechar() {
    this.equipeId = null;
    this.adicionandoMembro = false;
    this.funcionarios = [];
    this.formEquipe.reset({ id: null, nomeEquipe: '', identificador: '', ambulanciaId: null });
    this.visibleChange.emit(false);
  }
}
