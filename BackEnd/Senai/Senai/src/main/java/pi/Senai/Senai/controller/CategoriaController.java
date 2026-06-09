package pi.Senai.Senai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pi.Senai.Senai.entity.Categoria;
import pi.Senai.Senai.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/salvar")
    public void salvar(@RequestBody Categoria categoria) {
        categoriaService.salvar(categoria);
    }

    @PutMapping("/atualizar")
    public void atualizar(@RequestBody Categoria categoria) {
        categoriaService.atualizar(categoria);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable UUID id) {
        categoriaService.excluir(id);
    }

    @GetMapping("/listar")
    public List<Categoria> listar() {
        return categoriaService.listar();
    }
}
