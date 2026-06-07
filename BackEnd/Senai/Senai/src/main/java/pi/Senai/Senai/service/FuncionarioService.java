package pi.Senai.Senai.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Service
public class FuncionarioService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID Id;

    @Column
    private String Funcao;

    @Column
    private String Descricao;

    @Column
    private String IdFuncao;

    @Column
    private String IdUsuario;

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

    public String getIdFuncao() {
        return IdFuncao;
    }

    public void setIdFuncao(String idFuncao) {
        IdFuncao = idFuncao;
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    
}
