package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pi.Senai.Senai.entity.Funcionario;
import pi.Senai.Senai.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository _FuncionarioRepository;

    public Funcionario SalvarFuncionario(Funcionario funcionario){
        return _FuncionarioRepository.save(funcionario);
    }

    public Funcionario AtualizarFuncionario(Funcionario funcionario){
        if(!_FuncionarioRepository.existsById(funcionario.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não cadastrado, não encontrado");

        return _FuncionarioRepository.save(funcionario);
    }

    public void ExcluirFuncionario(UUID id){
        if(!_FuncionarioRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não cadastrado, não encontrado");

        _FuncionarioRepository.deleteById(id);
    }

    public List<Funcionario> ListarFuncionarios(){
        return _FuncionarioRepository.findAll();
    }

    public List<Funcionario> buscarPorFuncao(String funcao) {
        return _FuncionarioRepository.findByFuncao(funcao);
    }
}