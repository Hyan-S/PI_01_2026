import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { AmbulanciaService } from '../../core/services/ambulancia.service';
import { Ambulancia } from '../../core/models/ambulancia.model';

@Component({
  selector: 'app-ambulancia-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule,
    InputNumberModule,
    SelectModule,
    ButtonModule,
  ],
  templateUrl: './ambulancia-form.component.html',
  styleUrl: './ambulancia-form.component.css',
})
export class AmbulanciaFormComponent implements OnInit {
  formAmbulancia!: FormGroup;
  ambulanciaId: string | null = null;
  modoSomenteLeitura: boolean = false;

  opcoesStatus = [
    { label: 'Disponível', value: 'DISPONIVEL' },
    { label: 'A Caminho', value: 'A_CAMINHO' },
    { label: 'Em Atendimento', value: 'EM_ATENDIMENTO' },
    { label: 'Em Manutenção', value: 'EM_MANUTENCAO' },
  ];

  constructor(
    private fb: FormBuilder,
    private ambulanciaService: AmbulanciaService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
  ) {}

  ngOnInit() {
    this.iniciarFormulario();
    this.verificarRota();
  }

  iniciarFormulario() {
    this.formAmbulancia = this.fb.group({
      id: [null],
      descricao: ['', Validators.required],
      placa: ['', Validators.required],
      pesoBaseKg: [null, [Validators.required, Validators.min(0)]],
      observacao: [''],
      status: ['DISPONIVEL', Validators.required],
    });
  }

  verificarRota() {
    const urlSegment = this.route.snapshot.url[0]?.path;
    this.ambulanciaId = this.route.snapshot.paramMap.get('id');

    if (urlSegment === 'detalhes') {
      this.modoSomenteLeitura = true;
      this.formAmbulancia.disable();
    }

    if (this.ambulanciaId) {
      this.carregarDadosAmbulancia(this.ambulanciaId);
    }
  }

  carregarDadosAmbulancia(id: string) {
    this.ambulanciaService.listar().subscribe({
      next: (ambulancias) => {
        // Como o endpoint de buscar por ID não estava no controler enviado,
        // pegamos da lista para exemplificar. No mundo real, crie um endpoint GET /listar/{id} no Java
        const ambulancia = ambulancias.find((a) => a.id === id);
        if (ambulancia) {
          this.formAmbulancia.patchValue(ambulancia);
        }
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

    const ambulancia: Ambulancia = this.formAmbulancia.value;

    if (this.ambulanciaId) {
      this.ambulanciaService.atualizar(ambulancia).subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Ambulância atualizada!',
          });
          this.voltar();
        },
        error: () =>
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Falha ao atualizar.',
          }),
      });
    } else {
      this.ambulanciaService.salvar(ambulancia).subscribe({
        next: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Ambulância cadastrada!',
          });
          this.voltar();
        },
        error: () =>
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Falha ao cadastrar.',
          }),
      });
    }
  }

  voltar() {
    this.router.navigate(['/ambulancias']);
  }
}
