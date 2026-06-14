import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { MessageService } from 'primeng/api';

import { Equipe } from '../../core/models/equipe.model';
import { Ambulancia } from '../../core/models/ambulancia.model';
import { Funcionario } from '../../core/models/funcionario.model';
import { EquipeService } from '../../core/services/equipe.service';
import { AmbulanciaService } from '../../core/services/ambulancia.service';
import { FuncionarioService } from '../../core/services/funcionario.service';

@Component({
  selector: 'app-equipe-form',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, InputTextModule, InputNumberModule,
    SelectModule, ButtonModule, DialogModule, DividerModule,
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
  funcionariosDaEquipe: Funcionario[] = []; // Os que já estão na equipe
  
  // 💡 Novas variáveis para o Dropdown Dinâmico
  todosFuncionariosLivres: Funcionario[] = []; 
  funcionariosFiltrados: Funcionario[] = []; 
  cargos = [
    { label: 'Médico', value: 'MEDICO' },
    { label: 'Enfermeiro', value: 'ENFERMEIRO' },
    { label: 'Motorista', value: 'MOTORISTA' }
  ];

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
      this.funcionariosDaEquipe = [];

      if (this.equipeId) {
        this.carregarDados(this.equipeId);
        this.carregarFuncionariosDaEquipe(this.equipeId);
      } else {
        this.formEquipe.reset({ id: null, nomeEquipe: '', identificador: '', ambulanciaId: null });
      }
    }
  }

  iniciarFormularios() {
    this.formEquipe = this.fb.group({
      id: [null],
      nomeEquipe: ['', Validators.required],
      identificador: ['', [Validators.required, Validators.pattern(/^[A-Za-z]{2}-\d{3}$/)]],
      ambulanciaId: [null],
    });

    // 💡 Agora o formulário captura o Cargo e o ID do Funcionário
    this.formMembro = this.fb.group({
      cargo: ['', Validators.required],
      funcionarioId: [null, Validators.required],
    });

    // 💡 Reatividade: Filtra a lista de funcionários instantaneamente ao escolher o cargo
    this.formMembro.get('cargo')?.valueChanges.subscribe(cargoSelecionado => {
      if (cargoSelecionado) {
        this.funcionariosFiltrados = this.todosFuncionariosLivres.filter(
          f => f.funcao?.toUpperCase() === cargoSelecionado.toUpperCase()
        );
      } else {
        this.funcionariosFiltrados = [];
      }
      this.formMembro.get('funcionarioId')?.setValue(null);
    });
  }

  carregarAmbulanciasDiponiveis() {
    this.ambulanciaService.listar().subscribe({
      next: (lista) => (this.ambulancias = lista.filter(a => a.status === 'DISPONIVEL')),
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

  carregarFuncionariosDaEquipe(equipeId: string) {
    // 1. Busca quem já está na equipe para mostrar na lista
    this.funcionarioService.listarPorEquipe(equipeId).subscribe({
      next: (lista) => (this.funcionariosDaEquipe = lista),
    });
  }

  abrirPainelAdicionarMembro() {
    this.adicionandoMembro = true;
    this.formMembro.reset();
    
    // 2. Busca todos os funcionários do sistema e filtra os que NÃO estão em equipes
    // (Assumindo que seu método de listar todos se chama listarTodos() ou listar())
    this.funcionarioService.listarTodos().subscribe({
      next: (todos) => {
        // Pega apenas quem não tem equipe vinculada ainda
        this.todosFuncionariosLivres = todos.filter(f => !f.equipe || !f.equipe.id);
      }
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
      ambulancia: formValue.ambulanciaId ? { id: formValue.ambulanciaId } as Ambulancia : undefined,
    };

    if (this.equipeId) {
      this.equipeService.atualizar(equipe).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Equipe atualizada!' });
          this.salvo.emit();
          this.fechar();
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao atualizar.' })
      });
      return;
    }

    this.equipeService.salvar(equipe).subscribe({
      next: (criada) => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Equipe criada. Adicione os membros abaixo.' });
        this.salvo.emit();
        this.equipeId = criada.id!;
        this.titulo = 'Editar Equipe';
        this.formEquipe.patchValue({ id: criada.id });
        this.funcionariosDaEquipe = [];
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao salvar.' })
    });
  }

  adicionarMembro() {
    if (this.formMembro.invalid || !this.equipeId) return;

    const funcId = this.formMembro.value.funcionarioId;
    const funcionarioReal = this.todosFuncionariosLivres.find(f => f.id === funcId);

    if (!funcionarioReal) return;

    // 💡 ATUALIZA O VÍNCULO (Não cria um novo, apenas atualiza a chave estrangeira)
    funcionarioReal.equipe = { id: this.equipeId };

    this.funcionarioService.atualizar(funcionarioReal).subscribe({
      next: () => {
        this.adicionandoMembro = false;
        this.carregarFuncionariosDaEquipe(this.equipeId!); // Atualiza a lista da tela
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Funcionário vinculado à equipe.' });
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao vincular.' }),
    });
  }

  removerMembro(funcionario: Funcionario) {
    if (!funcionario.id) return;

    // 💡 DESVINCULA (Seta a equipe como nula, mas não exclui o funcionário do sistema)
    const payloadDesvinculo: any = { ...funcionario, equipe: null };

    this.funcionarioService.atualizar(payloadDesvinculo).subscribe({
      next: () => {
        this.carregarFuncionariosDaEquipe(this.equipeId!);
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Funcionário removido da equipe.' });
      },
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao desvincular.' })
    });
  }

  fechar() {
    this.equipeId = null;
    this.adicionandoMembro = false;
    this.funcionariosDaEquipe = [];
    this.formEquipe.reset({ id: null, nomeEquipe: '', identificador: '', ambulanciaId: null });
    this.visibleChange.emit(false);
  }
}