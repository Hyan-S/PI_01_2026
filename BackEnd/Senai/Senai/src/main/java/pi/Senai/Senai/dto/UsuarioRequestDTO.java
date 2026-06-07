package pi.Senai.Senai.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pi.Senai.Senai.entity.Usuario;
import pi.Senai.Senai.enums.NivelAcesso;

import java.util.UUID;

public class UsuarioRequestDTO {

    @NotBlank(message = "O nome é obrigatório e não pode ficar em branco.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @Email(message = "O e-mail informado possui um formato inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    private UUID categoriaId;

    @NotNull(message = "O nível de acesso é obrigatório.")
    private NivelAcesso nivelAcesso;

    public UsuarioRequestDTO() {
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public UUID getCategoriaId() { return categoriaId; }
    public void setCategoriaId(UUID categoriaId) { this.categoriaId = categoriaId; }
    public NivelAcesso getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }
}