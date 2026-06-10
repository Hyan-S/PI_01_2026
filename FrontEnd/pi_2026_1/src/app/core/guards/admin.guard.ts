import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { MessageService } from 'primeng/api';

export const adminGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const messageService = inject(MessageService);
  
  const usuario = authService.getUsuarioSessao();

  if (usuario && usuario.nivelAcesso === 'ADMIN') {
    return true;
  }

  messageService.add({ severity: 'error', summary: 'Acesso Negado', detail: 'Apenas administradores podem acessar esta área.' });
  router.navigate(['/dashboard']);
  return false;
};