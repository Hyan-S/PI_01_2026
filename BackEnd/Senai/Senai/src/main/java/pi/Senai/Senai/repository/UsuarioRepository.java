package pi.Senai.Senai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pi.Senai.Senai.entity.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByAtivoTrue();

    Optional<Usuario> findByIdAndAtivoIsTrue(UUID uuid);

    Optional<Usuario> findByCpf(String cpf);
}
