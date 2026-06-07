package pi.Senai.Senai.entity;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Permissao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;
    
    @Column
    private String Nome;

    @Column
    private String Descricao;

    @Column
    private Date DataCricao;

    @Column
    private Date UltimaAtualizacao;

    @Column
    private Boolean Ativo;

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

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Date getDataCricao() {
        return DataCricao;
    }

    public void setDataCricao(Date dataCricao) {
        DataCricao = dataCricao;
    }

    public Date getUltimaAtualizacao() {
        return UltimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        UltimaAtualizacao = ultimaAtualizacao;
    }

    public Boolean getAtivo() {
        return Ativo;
    }

    public void setAtivo(Boolean ativo) {
        Ativo = ativo;
    }

    
}
