package pi.Senai.Senai.Service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pi.Senai.Senai.Entity.Usuario;
import pi.Senai.Senai.dto.LoginRequestDTO;
import pi.Senai.Senai.dto.TokenResponseDTO;
import pi.Senai.Senai.repository.UsuarioRepository;

@Service
public class AutenticarService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AutenticarService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public TokenResponseDTO autenticar(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByCpf(dto.getLogin())
                .orElseGet(() -> usuarioRepository.findByEmail(dto.getLogin())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas.")));

        if (!usuario.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário inativo.");
        }

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas.");
        }

        String token = tokenService.gerarToken(usuario);
        return new TokenResponseDTO(token);
    }
}