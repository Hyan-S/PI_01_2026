package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Equipamento;
import pi.Senai.Senai.repository.EquipamentoRepository;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository _EquipamentoRepository;

    public void SalvarEquipamento(Equipamento equipamento){
        equipamento.setDataCriacao(new java.sql.Date(System.currentTimeMillis()));
        equipamento.setAtivo(true);

        _EquipamentoRepository.save(equipamento);
    }

    public void AtualizarEquipamento(Equipamento equipamento){
        if(!_EquipamentoRepository.existsById(equipamento.getId()))
            throw new RuntimeException("Equipamento não cadastrado, não encontrado");
        
        equipamento.setUltimaAtualizacao(new java.sql.Date(System.currentTimeMillis()));

        _EquipamentoRepository.save(equipamento);
    }

    public void ExcluirEquipamento(UUID id){
        if(!_EquipamentoRepository.existsById(id))
            throw new RuntimeException("Equipamento não cadastrado, não encontrado");

        var equipamento = _EquipamentoRepository.findById(id).get();

        equipamento.setAtivo(false);

        _EquipamentoRepository.save(equipamento);
    }

    public List<Equipamento> ListarEquipamentos(){
        return _EquipamentoRepository.findAll();
    }
}
