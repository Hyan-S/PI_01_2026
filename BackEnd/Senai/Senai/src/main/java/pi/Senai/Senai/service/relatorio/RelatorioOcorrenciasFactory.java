package pi.Senai.Senai.service.relatorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pi.Senai.Senai.repository.OcorrenciaRepository;

@Component("ocorrencias")
public class RelatorioOcorrenciasFactory extends RelatorioFactory {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Override
    protected Relatorio criarRelatorio() {
        List<Object> dados = new ArrayList<>(ocorrenciaRepository.findAll());
        return new RelatorioImpl("Relatório de Ocorrências", dados);
    }
}
