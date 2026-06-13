export interface Equipamento {
  id?: string;
  nome: string;
  quantidade: number;
  unidadeMedida: string;
  historico?: string;
  observacao?: string;
  ativo?: boolean;
  dataCriacao?: string;
  ultimaAtualizacao?: string;
}
