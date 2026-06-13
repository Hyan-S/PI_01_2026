import { Routes } from '@angular/router';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  // 1. REDIRECIONAMENTO RAIZ (Deve ser o PRIMEIRO da lista)
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  // 2. ROTA PÚBLICA (Fora do Layout)
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/pages/login-form/login-form')
        .then(m => m.LoginFormComponent)
  },

  // 3. ROTAS RESTRITAS (Dentro do Layout com Sidebar e Topbar)
  {
    path: '',
    loadComponent: () =>
      import('./shared/components/layout/layout.component')
        .then(m => m.LayoutComponent),
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./features/dashboard/pages/dashboard-principal/dashboard-principal')
            .then(m => m.DashboardPrincipal)
      },
      
      // 💡 Rota Atualizada: Apontando para o seu pacote correto de Gerenciamento
      {
        path: 'usuarios',
        canActivate: [adminGuard],
        loadComponent: () => 
          import('./features/users/pages/gerenciamento-usuario/gerenciamento-usuario/gerenciamento-usuario')
            .then(m => m.GerenciamentoUsuarioComponent)
      },

      // Rota de Cadastro
      {
        path: 'usuarios/cadastro',
        canActivate: [adminGuard],
        loadComponent: () =>
          import('./features/users/pages/cadastro-usuario/cadastro-usuario')
            .then(m => m.CadastroUsuarioComponent)
      },

      // Rota de Edição (Adicionada pela branch DevelopRefactor)
      {
        path: 'usuarios/editar/:id',
        canActivate: [adminGuard],
        loadComponent: () => 
          import('./features/users/pages/cadastro-usuario/cadastro-usuario')
            .then(m => m.CadastroUsuarioComponent)
      },

      {
        path: 'equipes',
        loadComponent: () =>
          import('./features/equipe-lista/equipe-lista.component').then(
            (m) => m.EquipeListaComponent,
          ),
      },

      // ROTAS DE AMBULÂNCIAS (Adicionadas pela branch feature/ambulanciaCompleta)
      {
        path: 'ambulancias',
        loadComponent: () => import('./features/ambulancia-lista/ambulancia-lista.component')
          .then(m => m.AmbulanciaListaComponent)
      },
      {
        path: 'ambulancias/novo',
        loadComponent: () => import('./features/ambulancia-form/ambulancia-form.component')
          .then(m => m.AmbulanciaFormComponent)
      },
      {
        path: 'ambulancias/editar/:id',
        loadComponent: () => import('./features/ambulancia-form/ambulancia-form.component')
          .then(m => m.AmbulanciaFormComponent)
      },
      {
        path: 'ambulancias/detalhes/:id',
        loadComponent: () => import('./features/ambulancia-form/ambulancia-form.component')
          .then(m => m.AmbulanciaFormComponent)
      }
    ]
  },

  // 4. CORINGA / FALLBACK (Qualquer URL não mapeada cai aqui)
  {
    path: '**',
    redirectTo: 'login'
  }
];