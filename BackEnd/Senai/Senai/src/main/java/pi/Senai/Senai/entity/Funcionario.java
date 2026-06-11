package pi.Senai.Senai.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;

@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    @Column
    private String Funcao;

    @Column
    private String Descricao;

    @Column
    private int NumeroFuncao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getFuncao() {
        return Funcao;
    }

    public void setFuncao(String funcao) {
        Funcao = funcao;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getNumeroFuncao() {
        return NumeroFuncao;
    }

    public void setNumeroFuncao(int numeroFuncao) {
        NumeroFuncao = numeroFuncao;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}
