package pi.Senai.Senai.repository;

import java.util.UUID;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pi.Senai.Senai.entity.Ocorrencia;

import pi.Senai.Senai.enums.StatusOcorrencia;


@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, UUID>{

    long countByProtocoloStartingWith(String prefixo);

    long countByStatusIn(List<StatusOcorrencia> statuses);
}