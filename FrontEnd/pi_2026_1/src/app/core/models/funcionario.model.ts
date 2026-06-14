// src/app/core/models/funcionario.model.ts
export interface Funcionario {
  id?: string;
  funcao: string;
  descricao?: string;
  numeroFuncao?: number;
  equipe?: { id: string } | null; 
}