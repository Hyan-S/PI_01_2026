package pi.Senai.Senai.dto;

import pi.Senai.Senai.entity.Usuario;
import pi.Senai.Senai.enums.NivelAcesso;

import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioResponseDTO {

    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private NivelAcesso nivelAcesso;
    private boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private String nomeCategoria;
    private String nomeFuncao;
    private UUID funcionarioId;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
        this.email = usuario.getEmail();
        this.nivelAcesso = usuario.getNivelAcesso();
        this.ativo = usuario.isAtivo();
        this.dataCadastro = usuario.getDataCadastro();
        this.dataAtualizacao = usuario.getDataAtualizacao();

        // Blindagem contra NullPointer e extração segura dos dados das tabelas filhas
        if (usuario.getCategoria() != null) {
            this.nomeCategoria = usuario.getCategoria().getDescricao();
        }
        
        if (usuario.getFuncionario() != null) {
            this.funcionarioId = usuario.getFuncionario().getId();
            this.nomeFuncao = usuario.getFuncionario().getFuncao();
        }
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public NivelAcesso getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
    public String getNomeFuncao() { return nomeFuncao; }
    public void setNomeFuncao(String nomeFuncao) { this.nomeFuncao = nomeFuncao; }
}