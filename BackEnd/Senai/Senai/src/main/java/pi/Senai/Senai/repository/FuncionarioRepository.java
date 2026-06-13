package pi.Senai.Senai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pi.Senai.Senai.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID>{

    java.util.List<Funcionario> findByFuncao(String funcao);

    java.util.List<Funcionario> findByEquipeId(UUID equipeId);
}
