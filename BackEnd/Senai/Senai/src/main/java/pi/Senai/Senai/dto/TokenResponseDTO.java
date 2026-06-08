package pi.Senai.Senai.dto;

public class TokenResponseDTO {
    private String token;
    private String tipo;
    private UsuarioResponseDTO usuario;

    public TokenResponseDTO(String token, UsuarioResponseDTO usuario){
        this.token=token;
        this.tipo="Bearer";
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
}
