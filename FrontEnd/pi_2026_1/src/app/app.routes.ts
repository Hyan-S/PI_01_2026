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
      /*{
        path: 'dashboard',
        // Crie este componente de teste ou comente essa rota se ele não existir ainda
        loadComponent: () => 
          import('./features/dashboard/pages/dashboard-principal/dashboard-principal') 
            .then(m => m.DashboardPrincipalComponent)
      },*/
      {
        path: 'usuarios/cadastro',
        canActivate: [adminGuard],
        loadComponent: () => 
          import('./features/users/pages/cadastro-usuario/cadastro-usuario')
            .then(m => m.CadastroUsuarioComponent)
      }
    ]
  },

  // 4. CORINGA / FALLBACK (Qualquer URL não mapeada cai aqui)
  { 
    path: '**', 
    redirectTo: 'login' 
  }
];