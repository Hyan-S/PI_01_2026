import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

// Imports do PrimeNG
import { MenuModule } from 'primeng/menu';
import { MenuItem } from 'primeng/api';
import { AvatarModule } from 'primeng/avatar';
import { AuthService } from '../../../core/services/auth.service';
import { UsuarioSession } from '../../../core/models/usuario.model';


@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, MenuModule, AvatarModule],
  templateUrl: './layout.html',
  styleUrls: ['./layout.css']
})
export class LayoutComponent implements OnInit {
  menuItems: MenuItem[] | undefined;
  usuario: UsuarioSession | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    // 1. Recupera quem está logado
    this.usuario = this.authService.getUsuarioSessao();

    // 2. Constrói o menu baseado no cargo da pessoa
    this.construirMenu();
  }

  construirMenu() {
    const cargo = this.usuario?.nivelAcesso || '';

    // BLOCO 1: Rotas comuns a TODOS os usuários (Motorista, Médico, Operador, Admin)
    const itens: MenuItem[] = [
      {
        label: 'Principal',
        items: [
          { label: 'Dashboard', icon: 'pi pi-home', routerLink: '/dashboard' },
          { label: 'Ocorrências', icon: 'pi pi-bolt', routerLink: '/ocorrencias' }
        ]
      }
    ];

    // BLOCO 2: Rotas de Gestão (Visível apenas para Administradores e Operadores)
    if (cargo === 'ADMIN' || cargo === 'OPERADOR') {
      const itensGestao: MenuItem[] = [
        { label: 'Equipes', icon: 'pi pi-users', routerLink: '/equipes' },
        { label: 'Ambulâncias', icon: 'pi pi-car', routerLink: '/ambulancias' },
        { label: 'Equipamentos', icon: 'pi pi-box', routerLink: '/equipamentos' },
      ];

      // Apenas o ADMIN pode gerenciar outros Usuários
      if (cargo === 'ADMIN') {
        itensGestao.unshift({ label: 'Usuários', icon: 'pi pi-user-edit', routerLink: '/usuarios' });
      }

      itens.push({
        label: 'Gestão',
        items: itensGestao
      });
    }

    // BLOCO 3: Ações do Sistema
    itens.push({
      label: 'Conta',
      items: [
        {
          label: 'Sair do Sistema',
          icon: 'pi pi-sign-out',
          command: () => this.sair()
        }
      ]
    });

    this.menuItems = itens;
  }

  sair() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
