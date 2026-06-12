package pi.Senai.Senai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pi.Senai.Senai.decorator.AmbulanciaEquipavel;
import pi.Senai.Senai.decorator.RecursoDeEmergencia;
import pi.Senai.Senai.decorator.itens.*;
import pi.Senai.Senai.dto.AmbulanciaEquipadaDTO;
import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.enums.TipoItemMedico;
import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.service.base.CrudBaseService;

import java.util.*;
import java.util.function.Function;

@Service
public class AmbulanciaService extends CrudBaseService<Ambulancia, UUID> {

    @Autowired
    private AmbulanciaRepository repositorio;

    private static final Map<TipoItemMedico, Function<RecursoDeEmergencia, RecursoDeEmergencia>> DECORADORES;

    static {
        DECORADORES = new EnumMap<>(TipoItemMedico.class);
        DECORADORES.put(TipoItemMedico.DESFIBRILADOR, DesfibrilladorDecorator::new);
        DECORADORES.put(TipoItemMedico.KIT_PRIMEIROS_AUXILIOS, KitPrimeirosAuxiliosDecorator::new);
        DECORADORES.put(TipoItemMedico.MACA, MacaDecorator::new);
        DECORADORES.put(TipoItemMedico.OXIGENIO, OxigenioDecorator::new);
        DECORADORES.put(TipoItemMedico.RESPIRADOR, RespiradorDecorator::new);
    }

    @Override
    public List<Ambulancia> listar() {
        return repositorio.findByAtivoTrue();
    }

    @Override
    protected void prepararParaAtualizar(Ambulancia entidade) {
        Ambulancia existente = repositorio.findById(entidade.getId()).orElse(null);
        if (existente != null) {
            entidade.setAtivo(existente.isAtivo());
            entidade.setDataCriacao(existente.getDataCriacao());
        }
    }

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

    public AmbulanciaEquipadaDTO equipar(UUID id, List<TipoItemMedico> itens) {
        Ambulancia ambulancia = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException(getMensagemNaoEncontrado()));

        ambulancia.setItensMedicos(Set.copyOf(itens));
        repositorio.save(ambulancia);

        return montarCadeia(ambulancia);
    }

    public AmbulanciaEquipadaDTO obterEquipada(UUID id) {
        Ambulancia ambulancia = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException(getMensagemNaoEncontrado()));

        return montarCadeia(ambulancia);
    }

    public Ambulancia buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Ambulância não encontrada com o ID: " + id));
    }

    private AmbulanciaEquipadaDTO montarCadeia(Ambulancia ambulancia) {
        RecursoDeEmergencia recurso = new AmbulanciaEquipavel(ambulancia);

        for (TipoItemMedico item : ambulancia.getItensMedicos()) {
            recurso = DECORADORES.get(item).apply(recurso);
        }

        return new AmbulanciaEquipadaDTO(
                recurso.getDescricao(),
                recurso.getItensEquipados()
        );
    }
}
