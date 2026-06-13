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
      
      // === ROTAS DE USUÁRIOS (IAM / CREDENCIAIS) ===
      {
        path: 'usuarios',
        canActivate: [adminGuard],
        loadComponent: () =>
          import('./features/users/pages/gerenciamento-usuario/gerenciamento-usuario/gerenciamento-usuario')
            .then(m => m.GerenciamentoUsuarioComponent)
      },
      {
        path: 'equipamentos',
        loadComponent: () => import('./features/equipamento-lista/equipamento-lista.component')
          .then(m => m.EquipamentoListaComponent)
      },

      // Rota de Cadastro
      {
        path: 'usuarios/cadastro',
        canActivate: [adminGuard],
        loadComponent: () =>
          import('./features/users/pages/cadastro-usuario/cadastro-usuario')
            .then(m => m.CadastroUsuarioComponent)
      },
      {
        path: 'usuarios/editar/:id',
        canActivate: [adminGuard],
        loadComponent: () =>
          import('./features/users/pages/cadastro-usuario/cadastro-usuario')
            .then(m => m.CadastroUsuarioComponent)
      },

      // === ROTAS OPERACIONAIS (OCORRÊNCIAS E EQUIPES) ===
      {
        path: 'ocorrencias',
        loadComponent: () =>
          import('./features/ocorrencia-lista/ocorrencia-lista.component')
            .then(m => m.OcorrenciaListaComponent),
      },
      {
        path: 'equipes',
        loadComponent: () =>
          import('./features/equipe-lista/equipe-lista.component')
            .then(m => m.EquipeListaComponent),
      },

      // === ROTAS DE AMBULÂNCIAS ===
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
      },

      // === ROTAS DE FUNCIONÁRIOS (ADICIONADO) ===
      {
        path: 'funcionarios',
        // ATENÇÃO: Se o adminGuard só deixar o ADMIN passar, o REGULADOR não vai conseguir entrar aqui.
        canActivate: [adminGuard], 
        loadComponent: () => import('./features/funcionarios/pages/funcionarios-list/funcionarios-list')
          .then(m => m.FuncionarioListComponent)
      },/*
      {
        path: 'funcionarios/cadastro',
        canActivate: [adminGuard],
        loadComponent: () => import('./features/funcionarios/pages/funcionario-form/funcionario-form.component')
          .then(m => m.FuncionarioFormComponent) // Substitua pelo nome real do componente quando criar
      },
      {
        path: 'funcionarios/editar/:id',
        canActivate: [adminGuard],
        loadComponent: () => import('./features/funcionarios/pages/funcionario-form/funcionario-form.component')
          .then(m => m.FuncionarioFormComponent) // Substitua pelo nome real do componente quando criar
      }*/
    ]
  },

  // 4. CORINGA / FALLBACK (Qualquer URL não mapeada cai aqui)
  {
    path: '**',
    redirectTo: 'login'
  }
];
