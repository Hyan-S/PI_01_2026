package pi.Senai.Senai.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Ocorrencia;
import pi.Senai.Senai.entity.StatusOcorrencia;
import pi.Senai.Senai.repository.OcorrenciaRepository;
import pi.Senai.Senai.service.iterator.ListaOcorrencias;

@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository _OcorrenciaRepository;

    public void SalvarOcorrencia(Ocorrencia ocorrencia){
        ocorrencia.setDataHoraAbertura(LocalDateTime.now());

        if(ocorrencia.getStatus() == null)
            ocorrencia.setStatus(StatusOcorrencia.AGUARDANDO);

        ocorrencia.setProtocolo(GerarProtocolo());

        _OcorrenciaRepository.save(ocorrencia);
    }

    public void AtualizarOcorrencia(Ocorrencia ocorrencia){
        if(!_OcorrenciaRepository.existsById(ocorrencia.getId()))
            throw new RuntimeException("Ocorrência não cadastrada, não encontrada");

        _OcorrenciaRepository.save(ocorrencia);
    }

    public void ExcluirOcorrencia(UUID id){
        if(!_OcorrenciaRepository.existsById(id))
            throw new RuntimeException("Ocorrência não cadastrada, não encontrada");

        var ocorrencia = _OcorrenciaRepository.findById(id).get();

        ocorrencia.setStatus(StatusOcorrencia.CANCELADA);
        ocorrencia.setDataHoraEncerramento(LocalDateTime.now());

        _OcorrenciaRepository.save(ocorrencia);
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

    private String GerarProtocolo(){
        String dataFormatada = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefixo = "OC-" + dataFormatada + "-";

        long sequencial = _OcorrenciaRepository.countByProtocoloStartingWith(prefixo) + 1;

        return String.format("%s%04d", prefixo, sequencial);
    }
}