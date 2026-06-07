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
import pi.Senai.Senai.entity.Permissao;
import pi.Senai.Senai.service.PermissaoService;

@RestController
@RequestMapping("/permissao")
public class PermisssaoController {
    
    @Autowired
    private PermissaoService _permissaoService;

    @PostMapping("/salvar")
    public void SalvarPermissao(@RequestBody Permissao permissao) {
        _permissaoService.SalvarPermissao(permissao);
    }

    @PutMapping("/atualizar")
    public void AtualizarPermissao(@RequestBody Permissao permissao) {
        _permissaoService.AtualizarPermissao(permissao);
    }

    @DeleteMapping("/excluir/{id}")
    public void ExcluirPermissao(@PathVariable UUID id) {
        _permissaoService.ExcluirPermissao(id);
    }

    @GetMapping("/listar")
    public List<Permissao> ListarPermissoes() {
        return _permissaoService.ListarPermissoes();
    }
}
