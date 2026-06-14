import { Ambulancia } from './ambulancia.model';

export interface Equipe {
  id?: string;
  nomeEquipe: string;
  identificador: string;
  ambulancia?: Ambulancia; 
}
