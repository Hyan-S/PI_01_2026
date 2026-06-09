package pi.Senai.Senai.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Categoria;
import pi.Senai.Senai.repository.CategoriaRepository;
import pi.Senai.Senai.service.base.CrudBaseService;

@Service
public class CategoriaService extends CrudBaseService<Categoria, UUID> {

    @Autowired
    private CategoriaRepository repositorio;

    @Override
    protected JpaRepository<Categoria, UUID> getRepositorio() {
        return repositorio;
    }

    @Override
    protected String getMensagemNaoEncontrado() {
        return "Categoria não encontrada";
    }

    @Override
    protected UUID getIdDaEntidade(Categoria entidade) {
        return entidade.getId();
    }
}
