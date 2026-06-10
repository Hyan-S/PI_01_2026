package pi.Senai.Senai.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.service.base.CrudBaseService;

@Service
public class AmbulanciaService extends CrudBaseService<Ambulancia, UUID> {

    @Autowired
    private AmbulanciaRepository repositorio;

    @Override
    protected JpaRepository<Ambulancia, UUID> getRepositorio() {
        return repositorio;
    }

    @Override
    protected String getMensagemNaoEncontrado() {
        return "Ambulância não encontrada";
    }

    @Override
    protected UUID getIdDaEntidade(Ambulancia entidade) {
        return entidade.getId();
    }
}
