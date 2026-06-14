package pi.Senai.Senai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pi.Senai.Senai.entity.Funcionario;
import pi.Senai.Senai.service.FuncionarioService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService _funcionarioService;

    @PostMapping("/salvar")
    public ResponseEntity<Funcionario> SalvarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario salvo = _funcionarioService.SalvarFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Funcionario> AtualizarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario atualizado = _funcionarioService.AtualizarFuncionario(funcionario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> ExcluirFuncionario(@PathVariable UUID id) {
        _funcionarioService.ExcluirFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Funcionario>> ListarFuncionarios() {
        return ResponseEntity.ok(_funcionarioService.ListarFuncionarios());
    }

    @GetMapping("/funcao/{funcao}")
    public ResponseEntity<List<Funcionario>> buscarPorFuncao(@PathVariable String funcao) {
        return ResponseEntity.ok(_funcionarioService.buscarPorFuncao(funcao));
    }

    @GetMapping("/equipe/{equipeId}")
    public ResponseEntity<List<Funcionario>> buscarPorEquipe(@PathVariable UUID equipeId) {
        return ResponseEntity.ok(_funcionarioService.buscarPorEquipe(equipeId));
    }
}