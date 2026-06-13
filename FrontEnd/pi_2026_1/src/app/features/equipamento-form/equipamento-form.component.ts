import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {SelectModule} from 'primeng/select';
import {ButtonModule} from 'primeng/button';
import {MessageService} from 'primeng/api';
import {DialogModule} from 'primeng/dialog';
import {TextareaModule} from 'primeng/textarea';
import {EquipamentoService} from '../../core/services/equipamento.service';
import {Equipamento} from '../../core/models/equipamento.model';

@Component({
  selector: 'app-equipamento-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, InputTextModule, TextareaModule, SelectModule, ButtonModule, DialogModule],
  templateUrl: './equipamento-form.component.html',
  styleUrl: './equipamento-form.component.css'
})
export class EquipamentoFormComponent implements OnInit, OnChanges {
  @Input() visible: boolean = false;
  @Input() equipamentoId: string | null = null;
  @Input() equipamentoData: Equipamento | null = null;
  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() salvo = new EventEmitter<void>();

  titulo: string = 'Novo Equipamento';
  formEquipamento!: FormGroup;

  opcoesUnidade = [
    {label: 'Unidade', value: 'UNIDADE'},
    {label: 'Caixa', value: 'CAIXA'},
    {label: 'Litro', value: 'LITRO'},
    {label: 'Mililitro', value: 'MILILITRO'},
    {label: 'Ampola', value: 'AMPOLA'},
    {label: 'Grama', value: 'GRAMA'},
    {label: 'Pacote', value: 'PACOTE'}
  ];

  constructor(
    private fb: FormBuilder,
    private equipamentoService: EquipamentoService,
    private messageService: MessageService
  ) {
  }

  ngOnInit() {
    this.iniciarFormulario();
    if (this.equipamentoData) {
      this.preencherFormulario();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (!this.formEquipamento) return;

    if (changes['visible'] && this.visible) {
      this.titulo = this.equipamentoId ? 'Editar Equipamento' : 'Novo Equipamento';

      if (this.equipamentoId && this.equipamentoData) {
        this.formEquipamento.patchValue(this.equipamentoData);
        this.formEquipamento.get('quantidade')?.disable();
      } else {
        this.formEquipamento.reset();
        this.formEquipamento.get('quantidade')?.enable();
      }
    }
  }

  iniciarFormulario() {
    this.formEquipamento = this.fb.group({
      id: [null],
      nome: ['', Validators.required],
      quantidade: [0, [Validators.required, Validators.min(0)]],
      unidadeMedida: ['', Validators.required],
      observacao: ['']
    });
  }

  salvar() {
    if (this.formEquipamento.invalid) return;

    const formValues = this.formEquipamento.getRawValue();
    const equipamento = {
      ...this.equipamentoData,
      ...formValues
    };
    const request = this.equipamentoId
      ? this.equipamentoService.atualizar(equipamento)
      : this.equipamentoService.salvar(equipamento);

    request.subscribe({
      next: () => {
        this.messageService.add({severity: 'success', summary: 'Sucesso', detail: 'Equipamento salvo!'});
        this.salvo.emit();
        this.fechar();
      },
      error: () => this.messageService.add({severity: 'error', summary: 'Erro', detail: 'Falha ao salvar.'})
    });
  }

  private preencherFormulario() {
    this.formEquipamento.patchValue({
      id: this.equipamentoData?.id,
      nome: this.equipamentoData?.nome,
      quantidade: this.equipamentoData?.quantidade,
      unidadeMedida: this.equipamentoData?.unidadeMedida,
      observacao: this.equipamentoData?.observacao
    });
    this.formEquipamento.get('quantidade')?.disable();
  }

  fechar() {
    this.visibleChange.emit(false);
  }
}
