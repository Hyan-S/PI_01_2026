package pi.Senai.Senai.entity.base;

import java.sql.Date;

public interface EntidadeGerenciavel {
    void setAtivo(boolean ativo);
    void setDataCriacao(Date data);
    void setUltimaAtualizacao(Date data);
}
