import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

// Imports do PrimeNG
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';   // 💡 Novo import
import { ToastModule } from 'primeng/toast'; // 💡 Novo import
import { MessageService } from 'primeng/api';

import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [
    CommonModule, 
    ReactiveFormsModule, 
    InputTextModule, 
    ButtonModule,
    CardModule,   
    ToastModule  
  ],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginFormComponent implements OnInit {
  formLogin!: FormGroup;
  carregando = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.formLogin = this.fb.group({
      login: ['', Validators.required],
      senha: ['', Validators.required]
    });
  }

  entrar() {
    if (this.formLogin.invalid) {
      this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha usuário e senha.' });
      return;
    }

    this.carregando = true;
    const credenciais = this.formLogin.value;

    this.authService.login(credenciais).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.carregando = false;
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Credenciais inválidas!' });
      }
    });
  }
}