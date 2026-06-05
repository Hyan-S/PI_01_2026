package pi.Senai.Senai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pi.Senai.Senai.Entity.Usuario;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
