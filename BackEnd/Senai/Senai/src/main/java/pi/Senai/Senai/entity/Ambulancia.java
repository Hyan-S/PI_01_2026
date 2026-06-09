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
public class Ambulancia implements EntidadeGerenciavel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    @Column
    private String Descricao;

    @Column
    private String Placa;

    @Column
    private String Observacao;

    @Column
    private double PesoBaseKg;

    @Column
    private Date DataCriacao;

    @Column
    private Date UltimaAtualizacao;

    @Column
    private boolean Ativo;

    public UUID getId() { return Id; }
    public void setId(UUID id) { Id = id; }

    public String getDescricao() { return Descricao; }
    public void setDescricao(String descricao) { Descricao = descricao; }

    public String getPlaca() { return Placa; }
    public void setPlaca(String placa) { Placa = placa; }

    public String getObservacao() { return Observacao; }
    public void setObservacao(String observacao) { Observacao = observacao; }

    public double getPesoBaseKg() { return PesoBaseKg; }
    public void setPesoBaseKg(double pesoBaseKg) { PesoBaseKg = pesoBaseKg; }

    public Date getDataCriacao() { return DataCriacao; }
    public void setDataCriacao(Date dataCriacao) { DataCriacao = dataCriacao; }

    public Date getUltimaAtualizacao() { return UltimaAtualizacao; }
    public void setUltimaAtualizacao(Date ultimaAtualizacao) { UltimaAtualizacao = ultimaAtualizacao; }

    public boolean isAtivo() { return Ativo; }
    public void setAtivo(boolean ativo) { Ativo = ativo; }
}
