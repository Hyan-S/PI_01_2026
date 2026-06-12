package pi.Senai.Senai.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import pi.Senai.Senai.entity.base.EntidadeGerenciavel;
import pi.Senai.Senai.enums.TipoItemMedico;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ambulancia_itens", joinColumns = @JoinColumn(name = "ambulancia_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item")
    private Set<TipoItemMedico> ItensMedicos = new HashSet<>();

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

    public Set<TipoItemMedico> getItensMedicos() { return ItensMedicos; }
    public void setItensMedicos(Set<TipoItemMedico> itensMedicos) { ItensMedicos = itensMedicos; }
}
