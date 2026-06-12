package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Equipamento;
import pi.Senai.Senai.repository.EquipamentoRepository;
import pi.Senai.Senai.service.base.CrudBaseService;

@Service
public class EquipamentoService extends CrudBaseService<Equipamento, UUID> {

    @Autowired
    private EquipamentoRepository repositorio;

    @Override
    protected JpaRepository<Equipamento, UUID> getRepositorio() {
        return repositorio;
    }

    @Override
    protected String getMensagemNaoEncontrado() {
        return "Equipamento não encontrado";
    }

    @Override
    protected UUID getIdDaEntidade(Equipamento entidade) {
        return entidade.getId();
    }

    public List<Equipamento> buscarPorNome(String nome) {
        return repositorio.findByNomeContainingIgnoreCase(nome);
    }
}
