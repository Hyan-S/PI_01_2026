package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Categoria;
import pi.Senai.Senai.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository _CategoriaRepository;

    public void SalvarCategoria(Categoria categoria){
        categoria.setDataCriacao(new java.sql.Date(System.currentTimeMillis()));
        categoria.setAtivo(true);

        _CategoriaRepository.save(categoria);
    }

    public void AtualizarCategoria(Categoria categoria){
        if(!_CategoriaRepository.existsById(categoria.getId()))
            throw new RuntimeException("Categoria não cadastrada, não encontrada");
        
        categoria.setUltimaAtualizacao(new java.sql.Date(System.currentTimeMillis()));

        _CategoriaRepository.save(categoria);
    }

    public void ExcluirCategoria(UUID id){
        if(!_CategoriaRepository.existsById(id))
            throw new RuntimeException("Categoria não cadastrada, não encontrada");

        var categoria = _CategoriaRepository.findById(id).get();

        categoria.setAtivo(false);

        _CategoriaRepository.save(categoria);
    }

    public List<Categoria> ListarCategorias(){
        return _CategoriaRepository.findAll();
    }
}
