import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputMaskModule } from 'primeng/inputmask';
import { PasswordModule } from 'primeng/password';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { UsuarioService } from '../../../../core/services/usuario.service';

@Component({
  selector: 'app-cadastro-usuario',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, InputTextModule, 
    InputMaskModule, PasswordModule, SelectModule, ButtonModule
  ],
  templateUrl: './cadastro-usuario.html'
})
export class CadastroUsuarioComponent implements OnInit {
  formUsuario!: FormGroup;
  niveisAcesso = [
    { label: 'Administrador', value: 'ADMIN' },
    { label: 'Operador (Regulador)', value: 'OPERADOR' },
    { label: 'Motorista', value: 'MOTORISTA' }
  ];

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.formUsuario = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/)]],
      email: ['', Validators.email],
      nivelAcesso: ['', Validators.required],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      confirmarSenha: ['', Validators.required]
    }, { validators: this.senhasIguaisValidator });
  }

  // Validador customizado para conferir as senhas
  senhasIguaisValidator(control: AbstractControl): ValidationErrors | null {
    const senha = control.get('senha')?.value;
    const confirmarSenha = control.get('confirmarSenha')?.value;
    return senha === confirmarSenha ? null : { senhasDiferentes: true };
  }

  salvar() {
    if (this.formUsuario.invalid) {
      this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Verifique os campos obrigatórios e se as senhas conferem.' });
      return;
    }

    // Extrai os dados, ignorando o campo 'confirmarSenha' que não vai pro backend
    const { confirmarSenha, ...dadosRequisicao } = this.formUsuario.value;

    this.usuarioService.cadastrar(dadosRequisicao).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Usuário cadastrado com sucesso!' });
        this.formUsuario.reset();
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.message || 'Falha ao cadastrar usuário.' });
      }
    });
  }

  voltar() {
    this.router.navigate(['/dashboard']);
  }
}