package pi.Senai.Senai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import pi.Senai.Senai.entity.Categoria;
import pi.Senai.Senai.service.CategoriaService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService _categoriaService;

    @PostMapping("/salvar")
    public void SalvarCategoria(@RequestBody Categoria categoria) {
        _categoriaService.SalvarCategoria(categoria);
    }

    @PutMapping("/atualizar")
    public void AtualizarCategoria(@RequestBody Categoria categoria) {
        _categoriaService.AtualizarCategoria(categoria);
    }

    @DeleteMapping("/excluir/{id}")
    public void ExcluirCategoria(@PathVariable UUID id) {
        _categoriaService.ExcluirCategoria(id);
    }

    @GetMapping("/listar")
    public List<Categoria> ListarCategorias() {
        return _categoriaService.ListarCategorias();
    }

}
