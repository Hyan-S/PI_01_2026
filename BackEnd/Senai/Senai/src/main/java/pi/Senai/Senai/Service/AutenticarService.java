package pi.Senai.Senai.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pi.Senai.Senai.dto.LoginRequestDTO;
import pi.Senai.Senai.dto.TokenResponseDTO;
import pi.Senai.Senai.dto.UsuarioResponseDTO; // 👈 Importante não esquecer este import
import pi.Senai.Senai.entity.Usuario;
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
        // 1. Busca o usuário por CPF ou E-mail
        Usuario usuario = usuarioRepository.findByCpf(dto.getLogin())
                .orElseGet(() -> usuarioRepository.findByEmail(dto.getLogin())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas.")));

        // 2. Valida se a conta não sofreu Soft Delete
        if (!usuario.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário inativo.");
        }

        // 3. Checa o hash da senha
        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas.");
        }

        // 4. Gera o Token JWT
        String token = tokenService.gerarToken(usuario);
        
        // 5. Converte o modelo de Banco (Entity) para o modelo de Visualização (DTO)
        UsuarioResponseDTO usuarioDto = new UsuarioResponseDTO(usuario);

        // 6. Retorna o combo completo (Token + Dados do Usuário)
        return new TokenResponseDTO(token, usuarioDto);
    }
}