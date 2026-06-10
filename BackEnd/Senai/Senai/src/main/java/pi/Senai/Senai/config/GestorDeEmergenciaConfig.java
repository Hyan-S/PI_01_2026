package pi.Senai.Senai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.repository.EquipeRepository;
import pi.Senai.Senai.repository.OcorrenciaRepository;
import pi.Senai.Senai.service.GestorDeEmergencia;

@Configuration
public class GestorDeEmergenciaConfig {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private AmbulanciaRepository ambulanciaRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Bean
    public GestorDeEmergencia gestorDeEmergencia() {
        GestorDeEmergencia gestor = GestorDeEmergencia.getInstance();
        gestor.setRepositorios(ocorrenciaRepository, ambulanciaRepository, equipeRepository);
        return gestor;
    }
}
