package pi.Senai.Senai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pi.Senai.Senai.entity.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, UUID> {

    long countByAmbulanciaIsNotNull();
}
