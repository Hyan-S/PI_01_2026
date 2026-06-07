package pi.Senai.Senai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pi.Senai.Senai.entity.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, UUID>{
    
}
