export interface UsuarioSession {
  id: string;
  nome: string;
  cpf: string;
  email: string;
  nivelAcesso: string;
}

export interface LoginResponse {
  token: string;
  tipo: string;
  usuario: UsuarioSession;
}

export interface UsuarioRequest {
  nome: string;
  cpf: string;
  email?: string;
  senha: string;
  nivelAcesso: string;
  categoriaId?: string;
}