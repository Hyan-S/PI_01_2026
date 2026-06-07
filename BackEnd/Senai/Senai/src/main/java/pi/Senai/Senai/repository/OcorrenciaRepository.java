package pi.Senai.Senai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pi.Senai.Senai.entity.Ocorrencia;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, UUID>{

    long countByProtocoloStartingWith(String prefixo);
}