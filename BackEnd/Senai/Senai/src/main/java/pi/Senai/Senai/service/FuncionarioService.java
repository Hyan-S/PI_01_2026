package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Funcionario;
import pi.Senai.Senai.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository _FuncionarioRepository;

    public void SalvarFuncionario(Funcionario funcionario){
        _FuncionarioRepository.save(funcionario);
    }

    public void AtualizarFuncionario(Funcionario funcionario){
        if(!_FuncionarioRepository.existsById(funcionario.getId()))
            throw new RuntimeException("Funcionário não cadastrado, não encontrado");

        _FuncionarioRepository.save(funcionario);
    }

    public void ExcluirFuncionario(UUID id){
        if(!_FuncionarioRepository.existsById(id))
            throw new RuntimeException("Funcionário não cadastrado, não encontrado");

        _FuncionarioRepository.deleteById(id);
    }

    public List<Funcionario> ListarFuncionarios(){
        return _FuncionarioRepository.findAll();
    }

    public List<Funcionario> buscarPorFuncao(String funcao) {
        return _FuncionarioRepository.findByFuncao(funcao);
    }

    public List<Funcionario> buscarPorEquipe(UUID equipeId) {
        return _FuncionarioRepository.findByEquipeId(equipeId);
    }
}