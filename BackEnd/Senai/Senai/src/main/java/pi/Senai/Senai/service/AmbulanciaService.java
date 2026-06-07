package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.repository.AmbulanciaRepository;

@Service
public class AmbulanciaService {
    @Autowired
    private AmbulanciaRepository _AmbulanciaRepository;

    public void SalvarAmbulancia(Ambulancia ambulancia){
        ambulancia.setAtivo(true);

        _AmbulanciaRepository.save(ambulancia);
    }

    public void AtualizarAmbulancia(Ambulancia ambulancia){
        if(!_AmbulanciaRepository.existsById(ambulancia.getId()))
            throw new RuntimeException("Ambulância não cadastrada, não encontrada");

        _AmbulanciaRepository.save(ambulancia);
    }

    public void ExcluirAmbulancia(UUID id){
        if(!_AmbulanciaRepository.existsById(id))
            throw new RuntimeException("Ambulância não cadastrada, não encontrada");

        var ambulancia = _AmbulanciaRepository.findById(id).get();

        ambulancia.setAtivo(false);

        _AmbulanciaRepository.save(ambulancia);
    }

    public List<Ambulancia> ListarAmbulancias(){
        return _AmbulanciaRepository.findAll();
    }
}
