package pi.Senai.Senai.service;

import java.util.List;

import pi.Senai.Senai.enums.StatusOcorrencia;
import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.repository.EquipeRepository;
import pi.Senai.Senai.repository.OcorrenciaRepository;

// PATTERN: Singleton
public class GestorDeEmergencia {

    private static volatile GestorDeEmergencia instancia;

    private OcorrenciaRepository ocorrenciaRepository;
    private AmbulanciaRepository ambulanciaRepository;
    private EquipeRepository equipeRepository;

    private GestorDeEmergencia() {}

    public static GestorDeEmergencia getInstance() {
        if (instancia == null) {
            synchronized (GestorDeEmergencia.class) {
                if (instancia == null) {
                    instancia = new GestorDeEmergencia();
                }
            }
        }
        return instancia;
    }

    public void setRepositorios(OcorrenciaRepository ocorrenciaRepository,
                                AmbulanciaRepository ambulanciaRepository,
                                EquipeRepository equipeRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ambulanciaRepository = ambulanciaRepository;
        this.equipeRepository = equipeRepository;
    }

    public long contarOcorrenciasAtivas() {
        return ocorrenciaRepository.countByStatusIn(
                List.of(StatusOcorrencia.AGUARDANDO, StatusOcorrencia.EM_ATENDIMENTO));
    }

    public long contarAmbulanciasDisponiveis() {
        return ambulanciaRepository.countByAtivoTrue();
    }

    public long contarEquipesEmCampo() {
        return equipeRepository.countByAmbulanciaIsNotNull();
    }
}