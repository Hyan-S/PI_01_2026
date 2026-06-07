package pi.Senai.Senai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import pi.Senai.Senai.entity.Funcionario;
import pi.Senai.Senai.repository.FuncionarioRepository;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    
    @Autowired
    private FuncionarioRepository _funcionarioRepository;

    @PostMapping("/salvar")
    public void SalvarFuncionario(@RequestBody Funcionario funcionario) {
        _funcionarioRepository.save(funcionario);
    }

    @PutMapping("/atualizar")
    public void AtualizarFuncionario(@RequestBody Funcionario funcionario) {
        _funcionarioRepository.save(funcionario);
    }

    @DeleteMapping("/excluir/{id}")
    public void ExcluirFuncionario(@PathVariable UUID id) {
        _funcionarioRepository.deleteById(id);
    }

    @GetMapping("/listar")
    public List<Funcionario> ListarFuncionarios() {
        return _funcionarioRepository.findAll();
    }
}
