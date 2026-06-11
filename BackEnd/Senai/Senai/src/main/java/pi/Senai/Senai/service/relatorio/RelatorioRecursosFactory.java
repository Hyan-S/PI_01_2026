package pi.Senai.Senai.service.relatorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.repository.EquipamentoRepository;

@Component("recursos")
public class RelatorioRecursosFactory extends RelatorioFactory {

    @Autowired
    private AmbulanciaRepository ambulanciaRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Override
    protected Relatorio criarRelatorio() {
        List<Object> dados = new ArrayList<>();
        dados.addAll(ambulanciaRepository.findAll());
        dados.addAll(equipamentoRepository.findAll());
        return new RelatorioImpl("Relatório de Recursos", dados);
    }
}
