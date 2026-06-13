export interface Equipe {
  id?: string;
  nomeEquipe: string;
  identificador: string;
  ambulancia?: { id: string; placa?: string; descricao?: string };
}

export interface Funcionario {
  id?: string;
  funcao: string;
  descricao: string;
  numeroFuncao: number;
  equipe?: { id: string };
}
