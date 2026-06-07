package pi.Senai.Senai.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ambulancias")
public class Ambulancia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false, unique = true, length = 7)
    private String placa;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false)
    private boolean ativo = true;

    public Ambulancia() {
    }

    public Ambulancia(UUID id, String descricao, String placa, String observacoes, boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.placa = placa;
        this.observacoes = observacoes;
        this.ativo = ativo;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
