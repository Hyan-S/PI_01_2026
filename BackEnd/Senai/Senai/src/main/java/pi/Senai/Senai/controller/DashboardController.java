package pi.Senai.Senai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pi.Senai.Senai.dto.DashboardResumoDTO;
import pi.Senai.Senai.service.GestorDeEmergencia;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private GestorDeEmergencia gestorDeEmergencia;

    @GetMapping("/resumo")
    public DashboardResumoDTO resumo() {
        return new DashboardResumoDTO(
                gestorDeEmergencia.contarOcorrenciasAtivas(),
                gestorDeEmergencia.contarAmbulanciasDisponiveis(),
                gestorDeEmergencia.contarEquipesEmCampo()
        );
    }
}
