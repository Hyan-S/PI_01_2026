export interface Ambulancia {
  id?: string;
  descricao: string;
  placa: string;
  observacao?: string;
  pesoBaseKg: number;
  dataCriacao?: string;
  ultimaAtualizacao?: string;
  ativo?: boolean;
  status?: string;
  itensMedicos?: string[];
}
