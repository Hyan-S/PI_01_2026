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

import jakarta.validation.Valid;
import pi.Senai.Senai.entity.Ocorrencia;
import pi.Senai.Senai.service.OcorrenciaService;

@RestController
@RequestMapping("/ocorrencia")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService _ocorrenciaService;

    @PostMapping("/salvar")
    public void SalvarOcorrencia(@Valid @RequestBody Ocorrencia ocorrencia) {
        _ocorrenciaService.SalvarOcorrencia(ocorrencia);
    }

    @PutMapping("/atualizar")
    public void AtualizarOcorrencia(@Valid @RequestBody Ocorrencia ocorrencia) {
        _ocorrenciaService.AtualizarOcorrencia(ocorrencia);
    }

    @DeleteMapping("/excluir/{id}")
    public void ExcluirOcorrencia(@PathVariable UUID id) {
        _ocorrenciaService.ExcluirOcorrencia(id);
    }

    @GetMapping("/listar")
    public List<Ocorrencia> ListarOcorrencias() {
        return _ocorrenciaService.ListarOcorrencias();
    }
}