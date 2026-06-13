package pi.Senai.Senai.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pi.Senai.Senai.entity.Ocorrencia;
import pi.Senai.Senai.enums.GravidadeOcorrencia;
import pi.Senai.Senai.enums.StatusOcorrencia;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, UUID> {

    long countByProtocoloStartingWith(String prefixo);

    long countByStatusIn(List<StatusOcorrencia> statuses);

    Optional<Ocorrencia> findByProtocolo(String protocolo);

    List<Ocorrencia> findByStatus(StatusOcorrencia status);

    List<Ocorrencia> findByGravidade(GravidadeOcorrencia gravidade);
}
