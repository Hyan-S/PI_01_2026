package pi.Senai.Senai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pi.Senai.Senai.dto.RetiradaEstoqueDTO;
import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.entity.Equipamento;
import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.repository.EquipamentoRepository;
import pi.Senai.Senai.service.base.CrudBaseService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class EquipamentoService extends CrudBaseService<Equipamento, UUID> {

    @Autowired
    private EquipamentoRepository repositorio;

    @Autowired
    private AmbulanciaRepository ambulanciaRepository;

    @Override
    protected void prepararParaAtualizar(Equipamento entidade) {
        Equipamento existente = repositorio.findById(entidade.getId()).orElse(null);

        if (existente != null) {
            entidade.setAtivo(existente.isAtivo());
            entidade.setDataCriacao(existente.getDataCriacao());
            entidade.setHistorico(existente.getHistorico()); // Salva o histórico de sumir!

            entidade.setQuantidade(existente.getQuantidade());
        }
    }

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

    public void adicionarQuantidade(UUID id, Double quantidadeParaAdicionar) {
        if (quantidadeParaAdicionar == null || quantidadeParaAdicionar <= 0) {
            throw new IllegalArgumentException("A quantidade para adicionar deve ser maior que zero.");
        }

        Equipamento equipamento = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException(getMensagemNaoEncontrado()));

        double quantidadeAtual = equipamento.getQuantidade() != null ? equipamento.getQuantidade() : 0.0;

        equipamento.setQuantidade(quantidadeAtual + quantidadeParaAdicionar);
        repositorio.save(equipamento);
    }

    public void retirarQuantidade(UUID id, RetiradaEstoqueDTO retiradaDTO) {
        if (retiradaDTO.getQuantidade() == null || retiradaDTO.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade para retirar deve ser maior que zero.");
        }

        if (retiradaDTO.getIdAmbulancia() == null) {
            throw new IllegalArgumentException("A ambulância de destino deve ser informada.");
        }

        Equipamento equipamento = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException(getMensagemNaoEncontrado()));

        Ambulancia ambulanciaDestino = ambulanciaRepository.findById(retiradaDTO.getIdAmbulancia())
                .orElseThrow(() -> new RuntimeException("Ambulância de destino não encontrada."));

        double quantidadeAtual = equipamento.getQuantidade() != null ? equipamento.getQuantidade() : 0.0;

        if (quantidadeAtual < retiradaDTO.getQuantidade()) {
            throw new RuntimeException("Quantidade insuficiente! Quantidade atual: " + quantidadeAtual + " " + equipamento.getUnidadeMedida());
        }

        equipamento.setQuantidade(quantidadeAtual - retiradaDTO.getQuantidade());

        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String registro = String.format("[%s] Retirado %s %s para a Ambulância (ID: %s).\n",
                dataHora, retiradaDTO.getQuantidade(), equipamento.getUnidadeMedida(), ambulanciaDestino.getId());

        String historicoAtual = equipamento.getHistorico() != null ? equipamento.getHistorico() : "";
        equipamento.setHistorico(historicoAtual + registro);

        repositorio.save(equipamento);
    }
}
