package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.entity.Equipe;
import pi.Senai.Senai.repository.AmbulanciaRepository;
import pi.Senai.Senai.repository.EquipeRepository;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private AmbulanciaRepository ambulanciaRepository;

    public Equipe salvar(Equipe equipe) {

        if(equipe.getNomeEquipe() == null || equipe.getNomeEquipe().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Nome da equipe é obrigatório.");

        if(equipe.getIdentificador() == null || equipe.getIdentificador().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Identificador da equipe é obrigatório");

        String identificador = equipe.getIdentificador().toUpperCase().trim();
        equipe.setIdentificador(identificador);

        if (!identificador.matches("^[A-Z]{2}-\\d{3}$")) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de identificador inválido! Use o padrão LL-NNN (Exemplo: AB-123)."); 
    
        // BLINDAGEM
        vincularAmbulanciaReal(equipe);

        return equipeRepository.save(equipe);
    }

    public Equipe atualizar(Equipe equipe) {
        if (!equipeRepository.existsById(equipe.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada");
        
        vincularAmbulanciaReal(equipe);
        
        return equipeRepository.save(equipe);
    }

    private void vincularAmbulanciaReal(Equipe equipe) {
        if (equipe.getAmbulancia() != null && equipe.getAmbulancia().getId() != null) {
            Ambulancia ambReal = ambulanciaRepository.findById(equipe.getAmbulancia().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ambulância referenciada não existe."));
            
            equipe.setAmbulancia(ambReal);
        } else {
            equipe.setAmbulancia(null); 
        }
    }

    public void excluir(UUID id) {
        if (!equipeRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada");
        equipeRepository.deleteById(id);
    }

    public List<Equipe> listar() {
        return equipeRepository.findAll();
    }

    public Equipe buscarPorId(UUID id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada"));
    }

    public Equipe buscarPorIdentificador(String identificador) {
        return equipeRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada com o identificador: " + identificador));
    }
}