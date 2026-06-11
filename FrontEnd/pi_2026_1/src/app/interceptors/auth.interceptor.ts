import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // 1. Busca o token após o login
  const TOKEN = localStorage.getItem('TOKEN'); // Adapte esta chave se você usou outro nome no seu AuthService

  // 2. Se o token existir, nós "clonamos" a requisição original e injetamos o cabeçalho
  if (TOKEN) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${TOKEN}`
      }
    });
    
    // 3. Manda a requisição carimbada para frente
    return next(clonedRequest);
  }

  // 4. Se não tiver token (ex: tela de login), manda a requisição original sem carimbo
  return next(req);
};