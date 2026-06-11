package pi.Senai.Senai.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pi.Senai.Senai.service.relatorio.Relatorio;
import pi.Senai.Senai.service.relatorio.RelatorioFactory;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private Map<String, RelatorioFactory> fabricas;

    @GetMapping
    public Relatorio gerar(@RequestParam String tipo) {
        RelatorioFactory fabrica = fabricas.get(tipo);
        if (fabrica == null) {
            throw new RuntimeException("Tipo de relatório inválido: " + tipo);
        }
        return fabrica.gerarERetornar();
    }
}
