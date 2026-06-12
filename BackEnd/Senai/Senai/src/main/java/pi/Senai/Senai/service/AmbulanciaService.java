package pi.Senai.Senai.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.decorator.AmbulanciaEquipavel;
import pi.Senai.Senai.decorator.RecursoDeEmergencia;
import pi.Senai.Senai.decorator.itens.DesfibrilladorDecorator;
import pi.Senai.Senai.decorator.itens.KitPrimeirosAuxiliosDecorator;
import pi.Senai.Senai.decorator.itens.MacaDecorator;
import pi.Senai.Senai.decorator.itens.OxigenioDecorator;
import pi.Senai.Senai.decorator.itens.RespiradorDecorator;
import pi.Senai.Senai.dto.AmbulanciaEquipadaDTO;
import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.enums.TipoItemMedico;
import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.service.base.CrudBaseService;

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

    public Ambulancia buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException(getMensagemNaoEncontrado() + " com o ID: " + id));
    }

    public AmbulanciaEquipadaDTO equipar(UUID id, List<TipoItemMedico> itens) {
        Ambulancia ambulancia = buscarPorId(id);

        ambulancia.setItensMedicos(Set.copyOf(itens));
        repositorio.save(ambulancia);

        return montarCadeia(ambulancia);
    }

    public AmbulanciaEquipadaDTO obterEquipada(UUID id) {
        Ambulancia ambulancia = buscarPorId(id);
        return montarCadeia(ambulancia);
    }

    public Ambulancia buscarPorPlaca(String placa) {
        return repositorio.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Ambulância não encontrada com a placa: " + placa));
    }

    private AmbulanciaEquipadaDTO montarCadeia(Ambulancia ambulancia) {
        RecursoDeEmergencia recurso = new AmbulanciaEquipavel(ambulancia);

        for (TipoItemMedico item : ambulancia.getItensMedicos()) {
            recurso = DECORADORES.get(item).apply(recurso);
        }

        // DTO instanciado apenas com a descrição e a lista de itens!
        return new AmbulanciaEquipadaDTO(
                recurso.getDescricao(),
                recurso.getItensEquipados()
        );
    }
}