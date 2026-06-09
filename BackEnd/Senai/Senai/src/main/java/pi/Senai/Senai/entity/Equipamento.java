package pi.Senai.Senai.entity;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import pi.Senai.Senai.entity.base.EntidadeGerenciavel;

@Entity
public class Equipamento implements EntidadeGerenciavel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @Column(nullable = false)
    private String Nome; 

    @Column
    private String Historico;

    @Column
    private String Observacao;

    //Implementação futura
    @Column
    private String IdEquipe;

    @Column
    private Date DataCriacao;

    @Column
    private Date UltimaAtualizacao;

    @Column
    private boolean Ativo;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getHistorico() {
        return Historico;
    }

    public void setHistorico(String historico) {
        Historico = historico;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    public String getIdEquipe() {
        return IdEquipe;
    }

    public void setIdEquipe(String idEquipe) {
        IdEquipe = idEquipe;
    }

    public boolean isAtivo() {
        return Ativo;
    }

    public void setAtivo(boolean ativo) {
        Ativo = ativo;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getUltimaAtualizacao() {
        return UltimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        UltimaAtualizacao = ultimaAtualizacao;
    }
}
