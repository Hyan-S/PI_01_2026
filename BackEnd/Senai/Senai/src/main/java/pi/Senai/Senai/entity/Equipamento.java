package pi.Senai.Senai.entity;

import jakarta.persistence.*;
import pi.Senai.Senai.entity.base.EntidadeGerenciavel;
import pi.Senai.Senai.enums.UnidadeMedida;

import java.sql.Date;
import java.util.UUID;

@Entity
public class Equipamento implements EntidadeGerenciavel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String historico;

    @Column(nullable = false)
    private Double quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadeMedida unidadeMedida;

    @Column
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ambulancia_id")
    private Ambulancia ambulancia;

    @Column
    private Date dataCriacao;

    @Column
    private Date ultimaAtualizacao;

    @Column
    private boolean ativo;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getHistorico() { return historico; }
    public void setHistorico(String historico) { this.historico = historico; }

    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; } // Corrigido o this.quantidade

    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(UnidadeMedida unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Ambulancia getAmbulancia() { return ambulancia; }
    public void setAmbulancia(Ambulancia ambulancia) { this.ambulancia = ambulancia; }

    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

    public Date getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(Date ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}