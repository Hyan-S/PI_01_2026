package pi.Senai.Senai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.Senai.Senai.entity.Ambulancia;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmbulanciaRepository extends JpaRepository<Ambulancia, UUID> {
    List<Ambulancia> findByAtivoTrue();
    Optional<Ambulancia> findByIdAndAtivoTrue(UUID id);
    boolean existsByPlacaAndAtivoTrue(String placa);
    boolean existsByPlacaAndAtivoTrueAndIdNot(String placa, UUID id);
}