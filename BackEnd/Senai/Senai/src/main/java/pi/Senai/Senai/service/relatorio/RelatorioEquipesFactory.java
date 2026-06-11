package pi.Senai.Senai.service.relatorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pi.Senai.Senai.repository.EquipeRepository;

@Component("equipes")
public class RelatorioEquipesFactory extends RelatorioFactory {

    @Autowired
    private EquipeRepository equipeRepository;

    @Override
    protected Relatorio criarRelatorio() {
        List<Object> dados = new ArrayList<>(equipeRepository.findAll());
        return new RelatorioImpl("Relatório de Equipes", dados);
    }
}
