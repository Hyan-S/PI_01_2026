import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const TOKEN = localStorage.getItem('TOKEN');

  if (TOKEN) {
    const authReq = req.clone({
      setHeaders: { Authorization: `Bearer ${TOKEN}` },
    });
    return next(authReq);
  }

  return next(req);
};
