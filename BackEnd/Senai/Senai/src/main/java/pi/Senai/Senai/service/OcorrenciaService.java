package pi.Senai.Senai.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pi.Senai.Senai.dto.DespachoDTO;
import pi.Senai.Senai.entity.Ocorrencia;
import pi.Senai.Senai.enums.GravidadeOcorrencia;
import pi.Senai.Senai.enums.StatusAmbulancia;
import pi.Senai.Senai.enums.StatusOcorrencia;
import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.repository.OcorrenciaRepository;
import pi.Senai.Senai.service.iterator.ListaOcorrencias;



@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository _OcorrenciaRepository;

    @Autowired
    private AmbulanciaRepository _AmbulanciaRepository;

    @Transactional
    public Ocorrencia SalvarOcorrencia(Ocorrencia ocorrencia){
        ocorrencia.setDataHoraAbertura(LocalDateTime.now());

        if(ocorrencia.getStatus() == null)
            ocorrencia.setStatus(StatusOcorrencia.AGUARDANDO);

        ocorrencia.setProtocolo(GerarProtocolo());

        Ocorrencia salva = _OcorrenciaRepository.save(ocorrencia);

        sincronizarStatusAmbulancia(ocorrencia.getVeiculoId(), ocorrencia.getStatus());
        return salva;
    }

    @Transactional
    public void AtualizarOcorrencia(Ocorrencia ocorrencia){
        Ocorrencia ocorrenciaAntiga = _OcorrenciaRepository.findById(ocorrencia.getId())
                .orElseThrow(() -> new RuntimeException("Ocorrência não cadastrada, não encontrada"));

        ocorrencia.setDataHoraAbertura(ocorrenciaAntiga.getDataHoraAbertura());
        if (ocorrencia.getOperadorId() == null)
            ocorrencia.setOperadorId(ocorrenciaAntiga.getOperadorId());

        UUID veiculoAntigoId = ocorrenciaAntiga.getVeiculoId();
        UUID veiculoNovoId = ocorrencia.getVeiculoId();

        if (veiculoAntigoId != null && !veiculoAntigoId.equals(veiculoNovoId)) {
            atualizarStatusAmbulanciaUnico(veiculoAntigoId, StatusAmbulancia.DISPONIVEL);
        }

        _OcorrenciaRepository.save(ocorrencia);

        sincronizarStatusAmbulancia(veiculoNovoId, ocorrencia.getStatus());
    }

    @Transactional
    public void ExcluirOcorrencia(UUID id){
        Ocorrencia ocorrencia = _OcorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não cadastrada, não encontrada"));

        ocorrencia.setStatus(StatusOcorrencia.CANCELADA);
        ocorrencia.setDataHoraEncerramento(LocalDateTime.now());

        _OcorrenciaRepository.save(ocorrencia);

        if (ocorrencia.getVeiculoId() != null) {
            atualizarStatusAmbulanciaUnico(ocorrencia.getVeiculoId(), StatusAmbulancia.DISPONIVEL);
        }
    }

    @Transactional
    public void DespacharOcorrencia(UUID id, DespachoDTO dto) {
        Ocorrencia ocorrencia = _OcorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        ocorrencia.setVeiculoId(dto.getVeiculoId());
        if (dto.getEquipeId() != null)
            ocorrencia.setEquipeId(dto.getEquipeId());
        ocorrencia.setStatus(StatusOcorrencia.A_CAMINHO);

        _OcorrenciaRepository.save(ocorrencia);
        sincronizarStatusAmbulancia(dto.getVeiculoId(), StatusOcorrencia.A_CAMINHO);
    }

    public List<Ocorrencia> ListarOcorrencias(){
        return _OcorrenciaRepository.findAll();
    }

    public List<Ocorrencia> listarPorPrioridade() {
        ListaOcorrencias lista = new ListaOcorrencias(_OcorrenciaRepository.findAll());
        List<Ocorrencia> resultado = new java.util.ArrayList<>();
        lista.forEach(resultado::add);
        return resultado;
    }

    public Ocorrencia buscarPorProtocolo(String protocolo) {
        return _OcorrenciaRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada com o protocolo: " + protocolo));
    }

    public List<Ocorrencia> buscarPorStatus(StatusOcorrencia status) {
        return _OcorrenciaRepository.findByStatus(status);
    }

    public List<Ocorrencia> buscarPorGravidade(GravidadeOcorrencia gravidade) {
        return _OcorrenciaRepository.findByGravidade(gravidade);
    }

    private String GerarProtocolo(){
        String dataFormatada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefixo = "OC-" + dataFormatada + "-";

        long sequencial = _OcorrenciaRepository.countByProtocoloStartingWith(prefixo) + 1;

        return String.format("%s%04d", prefixo, sequencial);
    }

    private void sincronizarStatusAmbulancia(UUID veiculoId, StatusOcorrencia statusOcorrencia) {
        if (veiculoId == null) return;

        _AmbulanciaRepository.findById(veiculoId).ifPresent(ambulancia -> {

            StatusAmbulancia novoStatus = ambulancia.getStatus();

            switch (statusOcorrencia) {
                case A_CAMINHO:
                    novoStatus = StatusAmbulancia.A_CAMINHO;
                    break;
                case EM_ATENDIMENTO:
                    novoStatus = StatusAmbulancia.EM_ATENDIMENTO;
                    break;
                case ENCERRADA:
                    novoStatus = StatusAmbulancia.DISPONIVEL;
                    break;
                case CANCELADA:
                    novoStatus = StatusAmbulancia.DISPONIVEL;
                    break;
                case AGUARDANDO:
                    break;
            }

            if (ambulancia.getStatus() != novoStatus) {
                ambulancia.setStatus(novoStatus);
                _AmbulanciaRepository.save(ambulancia);
            }
        });
    }

    private void atualizarStatusAmbulanciaUnico(UUID veiculoId, StatusAmbulancia novoStatus) {
        _AmbulanciaRepository.findById(veiculoId).ifPresent(ambulancia -> {
            ambulancia.setStatus(novoStatus);
            _AmbulanciaRepository.save(ambulancia);
        });
    }
}