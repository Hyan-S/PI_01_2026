import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router'; // 💡 ActivatedRoute adicionado
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputMaskModule } from 'primeng/inputmask';
import { PasswordModule } from 'primeng/password';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { UsuarioService } from '../../../../core/services/usuario.service';
import { Toast } from 'primeng/toast';

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
  
  // 💡 Controle de Estado da Tela
  isEdicao: boolean = false;
  usuarioId: string | null = null;
  tituloPagina: string = 'Cadastro de Novo Usuário';

  // 💡 Dicionário Sincronizado com o Back-end
  niveisAcesso = [
    { label: 'Administrador', value: 'ADMIN' },
    { label: 'Operador', value: 'OPERADOR' },
    { label: 'Regulador', value: 'REGULADOR' },
    { label: 'Motorista', value: 'MOTORISTA' },
    { label: 'Médico', value: 'MEDICO' },
    { label: 'Enfermeiro', value: 'ENFERMEIRO' },
    { label: 'Solicitante', value: 'SOLICITANTE' }
  ];

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router,
    private route: ActivatedRoute, // Para ler a URL
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.iniciarFormulario();
    this.verificarModoEdicao();
  }

  iniciarFormulario() {
    this.formUsuario = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/)]],
      email: ['', Validators.email],
      nivelAcesso: ['', Validators.required],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      confirmarSenha: ['', Validators.required]
    }, { validators: this.senhasIguaisValidator });
  }

  verificarModoEdicao() {
    this.usuarioId = this.route.snapshot.paramMap.get('id');
    
    if (this.usuarioId) {
      this.isEdicao = true;
      this.tituloPagina = 'Editar Usuário';
      
      // 💡 Se é edição, a senha passa a ser opcional
      this.removerObrigatoriedadeSenha();
      this.carregarDadosUsuario(this.usuarioId);
    }
  }

  removerObrigatoriedadeSenha() {
    this.formUsuario.get('senha')?.clearValidators();
    this.formUsuario.get('senha')?.setValidators([Validators.minLength(6)]); // Se digitar, tem que ter no mínimo 6. Mas não é required.
    this.formUsuario.get('senha')?.updateValueAndValidity();

    this.formUsuario.get('confirmarSenha')?.clearValidators();
    this.formUsuario.get('confirmarSenha')?.updateValueAndValidity();
  }

  carregarDadosUsuario(id: string) {
    this.usuarioService.buscarPorId(id).subscribe({
      next: (usuario) => {
        // 💡 Preenche o formulário com os dados vindos do banco
        this.formUsuario.patchValue({
          nome: usuario.nome,
          cpf: usuario.cpf,
          email: usuario.email,
          nivelAcesso: usuario.nivelAcesso
        });
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível carregar os dados do usuário.' });
        this.voltar();
      }
    });
  }

  senhasIguaisValidator(control: AbstractControl): ValidationErrors | null {
    const senha = control.get('senha')?.value;
    const confirmarSenha = control.get('confirmarSenha')?.value;
    
    // Se a senha estiver vazia (em modo de edição), passa direto
    if (!senha && !confirmarSenha) return null;
    
    return senha === confirmarSenha ? null : { senhasDiferentes: true };
  }

  salvar() {
    if (this.formUsuario.invalid) {
      this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Verifique os campos obrigatórios e se as senhas conferem.' });
      return;
    }

    const { confirmarSenha, ...dadosRequisicao } = this.formUsuario.value;

    // 💡 Bifurcação Arquitetural: POST ou PUT?
    if (this.isEdicao && this.usuarioId) {
      this.usuarioService.editar(this.usuarioId, dadosRequisicao).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Usuário atualizado com sucesso!' });
          setTimeout(() => this.voltar(), 1500); // Dá tempo do usuário ler o Toast antes de redirecionar
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
    // 💡 Redireciona para a lista de usuários ao invés do dashboard genérico
    this.router.navigate(['/usuarios']);
  }
}