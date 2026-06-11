package pi.Senai.Senai.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import pi.Senai.Senai.dto.UsuarioRequestDTO;
import pi.Senai.Senai.dto.UsuarioResponseDTO;
import pi.Senai.Senai.entity.Usuario;
import pi.Senai.Senai.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        validadorFormatoCpf(dto.getCpf());

        if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha é obrigatória para o cadastro.");
        }

        if (usuarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O CPF informado já está cadastrado no sistema.");
        }

        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "O E-mail informado já está cadastrado.");
            }
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setNivelAcesso(dto.getNivelAcesso());
        usuario.setAtivo(true);

        usuario = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(UUID id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoIsTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado ou inativo. ID: " + id));

        validadorFormatoCpf(dto.getCpf());

        // Valida se o CPF pertence a OUTRO usuário
        usuarioRepository.findByCpf(dto.getCpf()).ifPresent(userExistente -> {
            if (!userExistente.getId().equals(usuario.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "O CPF informado já está em uso por outro usuário.");
            }
        });

        // Valida se o E-mail pertence a OUTRO usuário
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            usuarioRepository.findByEmail(dto.getEmail()).ifPresent(userExistente -> {
                if (!userExistente.getId().equals(usuario.getId())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "O E-mail informado já está em uso por outro usuário.");
                }
            });
            usuario.setEmail(dto.getEmail());
        }

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setNivelAcesso(dto.getNivelAcesso());

        // Se o front-end mandar uma senha nova, nós atualizamos o Hash. Se vier vazia, mantemos a antiga.
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return new UsuarioResponseDTO(usuarioRepository.save(usuario));
    }


    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findByAtivoTrue();
        return usuarios.stream().map(UsuarioResponseDTO::new).toList();
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoIsTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado ou inativo. ID: " + id));
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public void deletarLogico(UUID id) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoIsTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado ou já está inativo. ID: " + id));

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    private void validadorFormatoCpf(String cpf) {
        if (cpf == null || !cpf.matches("^([0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2})$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O CPF informado é inválido ou não segue o padrão exigido.");
        }
    }
}
