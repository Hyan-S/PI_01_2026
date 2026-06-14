import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router'; 
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputMaskModule } from 'primeng/inputmask';
import { PasswordModule } from 'primeng/password';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { Toast } from 'primeng/toast';

import { UsuarioService } from '../../../../core/services/usuario.service';
import { FuncionarioService } from '../../../../core/services/funcionario.service';
import { Funcionario } from '../../../../core/models/funcionario.model';

@Component({
  selector: 'app-cadastro-usuario',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, InputTextModule, 
    InputMaskModule, PasswordModule, SelectModule, ButtonModule, Toast
  ],
  templateUrl: './cadastro-usuario.html',
  styleUrl: './cadastro-usuario.css'
})
export class CadastroUsuarioComponent implements OnInit {
  formUsuario!: FormGroup;
  
  isEdicao: boolean = false;
  usuarioId: string | null = null;
  tituloPagina: string = 'Cadastro de Novo Usuário';

  niveisAcesso = [
    { label: 'Administrador', value: 'ADMIN' },
    { label: 'Operador', value: 'OPERADOR' },
    { label: 'Regulador', value: 'REGULADOR' },
    { label: 'Motorista', value: 'MOTORISTA' },
    { label: 'Médico', value: 'MEDICO' },
    { label: 'Enfermeiro', value: 'ENFERMEIRO' },
  ];

  todosFuncionarios: Funcionario[] = [];
  funcionariosFiltrados: Funcionario[] = [];

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private funcionarioService: FuncionarioService,
    private router: Router,
    private route: ActivatedRoute, 
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.carregarFuncionarios();
    this.iniciarFormulario();
    this.verificarModoEdicao();
  }

  carregarFuncionarios() {
    this.funcionarioService.listarTodos().subscribe({
      next: (dados) => {
        this.todosFuncionarios = dados;
        this.funcionariosFiltrados = dados;
      },
      error: () => {
        this.messageService.add({ severity: 'warn', summary: 'Aviso', detail: 'Falha ao carregar a lista de funcionários.' });
      }
    });
  }

  iniciarFormulario() {
    this.formUsuario = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/)]],
      email: ['', Validators.email],
      nivelAcesso: ['', Validators.required],
      funcionarioId: [null],    
      senha: ['', [Validators.required, Validators.minLength(6)]],
      confirmarSenha: ['', Validators.required]
    }, { validators: this.senhasIguaisValidator });

    this.formUsuario.get('nivelAcesso')?.valueChanges.subscribe(nivelSelecionado => {
      this.filtrarFuncionariosPorNivel(nivelSelecionado);
    });
  }

  filtrarFuncionariosPorNivel(nivel: string | null) {
    if (!nivel) {
      this.funcionariosFiltrados = this.todosFuncionarios;
      return;
    }

    this.funcionariosFiltrados = this.todosFuncionarios.filter(f => 
      f.funcao.toUpperCase() === nivel.toUpperCase()
    );

    const funcionarioIdControl = this.formUsuario.get('funcionarioId');
    if (funcionarioIdControl?.value) {
      const funcionarioAindaValido = this.funcionariosFiltrados.find(f => f.id === funcionarioIdControl.value);
      if (!funcionarioAindaValido) {
        funcionarioIdControl.setValue(null);
      }
    }
  }

  verificarModoEdicao() {
    this.usuarioId = this.route.snapshot.paramMap.get('id');
    
    if (this.usuarioId) {
      this.isEdicao = true;
      this.tituloPagina = 'Editar Usuário';
      this.removerObrigatoriedadeSenha();
      this.carregarDadosUsuario(this.usuarioId);
    }
  }

  removerObrigatoriedadeSenha() {
    this.formUsuario.get('senha')?.clearValidators();
    this.formUsuario.get('senha')?.setValidators([Validators.minLength(6)]); 
    this.formUsuario.get('senha')?.updateValueAndValidity();

    this.formUsuario.get('confirmarSenha')?.clearValidators();
    this.formUsuario.get('confirmarSenha')?.updateValueAndValidity();
  }

  carregarDadosUsuario(id: string) {
    this.usuarioService.buscarPorId(id).subscribe({
      next: (usuario) => {
        this.formUsuario.patchValue({
          nome: usuario.nome,
          cpf: usuario.cpf,
          email: usuario.email,
          nivelAcesso: usuario.nivelAcesso,
          funcionarioId: usuario.funcionarioId 
        });

        this.filtrarFuncionariosPorNivel(usuario.nivelAcesso);
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível carregar os dados do usuário.' });
        this.voltar();
      }
    });
  }

  senhasIguaisValidator(control: AbstractControl): ValidationErrors | null {
    const senha = control.get('senha')?.value;
    const confirmarSenha = control.get('confirmarSenha')?.value;
    
    if (!senha && !confirmarSenha) return null;
    return senha === confirmarSenha ? null : { senhasDiferentes: true };
  }

  salvar() {
    if (this.formUsuario.invalid) {
      this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Verifique os campos obrigatórios e se as senhas conferem.' });
      return;
    }

    const { confirmarSenha, ...dadosRequisicao } = this.formUsuario.value;

    if (this.isEdicao && this.usuarioId) {
      this.usuarioService.editar(this.usuarioId, dadosRequisicao).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Usuário atualizado com sucesso!' });
          setTimeout(() => this.voltar(), 1500); 
        },
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.message || 'Falha ao atualizar usuário.' });
        }
      });
    } else {
      this.usuarioService.cadastrar(dadosRequisicao).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Usuário cadastrado com sucesso!' });
          setTimeout(() => this.voltar(), 1500);
        },
        error: (err) => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error?.message || 'Falha ao cadastrar usuário.' });
        }
      });
    }
  }

  voltar() {
    this.router.navigate(['/usuarios']);
  }
}