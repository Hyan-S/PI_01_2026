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
import jakarta.persistence.PrePersist;
import pi.Senai.Senai.entity.base.EntidadeGerenciavel;
import pi.Senai.Senai.enums.TipoItemMedico;
import pi.Senai.Senai.enums.StatusAmbulancia;

@Entity
public class Ambulancia implements EntidadeGerenciavel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String descricao;

    @Column
    private String placa;

    @Column
    private String observacao;

    @Column
    private Date dataCriacao;

    @Column
    private Date ultimaAtualizacao;

    @Column
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAmbulancia status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ambulancia_itens", joinColumns = @JoinColumn(name = "ambulancia_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item")
    private Set<TipoItemMedico> ItensMedicos = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = StatusAmbulancia.DISPONIVEL;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    @Override
    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public StatusAmbulancia getStatus() {
        return status;
    }

    public void setStatus(StatusAmbulancia status) {
        this.status = status;
    }

    public Set<TipoItemMedico> getItensMedicos() {
        return ItensMedicos;
    }

    public void setItensMedicos(Set<TipoItemMedico> itensMedicos) {
        ItensMedicos = itensMedicos;
    }
}