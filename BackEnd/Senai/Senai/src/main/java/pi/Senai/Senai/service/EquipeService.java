package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void atualizar(Equipe equipe) {
        if (!equipeRepository.existsById(equipe.getId()))
            throw new RuntimeException("Equipe não encontrada");
        equipeRepository.save(equipe);
    }

    public void excluir(UUID id) {
        if (!equipeRepository.existsById(id))
            throw new RuntimeException("Equipe não encontrada");
        equipeRepository.deleteById(id);
    }

    public List<Equipe> listar() {
        return equipeRepository.findAll();
    }

    public Equipe buscarPorId(UUID id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
    }

    public Equipe buscarPorIdentificador(String identificador) {
        return equipeRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada com o identificador: " + identificador));
    }
    
}
