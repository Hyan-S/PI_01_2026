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

    public void salvar(Equipe equipe) {
        equipeRepository.save(equipe);
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
}
