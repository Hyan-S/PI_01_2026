package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pi.Senai.Senai.entity.Equipe;
import pi.Senai.Senai.repository.EquipeRepository;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    public Equipe salvar(Equipe equipe) {
        if(equipe.getIdentificador() == null || equipe.getIdentificador().isEmpty())
            throw new RuntimeException("Identificador da equipe é obrigatório");

        String identificador = equipe.getIdentificador().toUpperCase().trim();
        equipe.setIdentificador(identificador);

        if (!identificador.matches("^[A-Z]{2}-\\d{3}$")) 
            throw new RuntimeException("Formato de identificador inválido! Use o padrão LL-NNN (Exemplo: AB-123).");
    
        return equipeRepository.save(equipe);
    }

    public Equipe atualizar(Equipe equipe) {
        if (!equipeRepository.existsById(equipe.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipe não encontrada");
        return equipeRepository.save(equipe);
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