export interface Ocorrencia {
  id?: string;
  protocolo?: string;
  titulo: string;
  descricao: string;
  tipo: string;
  gravidade: string;
  status?: string;
  endereco: string;
  dataHoraAbertura?: string;
  dataHoraEncerramento?: string;
  operadorId?: string;
  equipeId?: string;
}
