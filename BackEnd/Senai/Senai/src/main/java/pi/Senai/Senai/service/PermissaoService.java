package pi.Senai.Senai.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pi.Senai.Senai.entity.Permissao;
import pi.Senai.Senai.repository.PermissaoRepository;

@Service
public class PermissaoService {
    
    @Autowired
    private PermissaoRepository _PermissaoRepository;

    public void SalvarPermissao(Permissao permissao){
        permissao.setDataCricao(new java.sql.Date(System.currentTimeMillis()));
        permissao.setAtivo(true);

        permissao.setUltimaAtualizacao(new java.sql.Date(System.currentTimeMillis()));

        _PermissaoRepository.save(permissao);
    }

    public void AtualizarPermissao(Permissao permissao){
        if(!_PermissaoRepository.existsById(permissao.getId()))
            throw new RuntimeException("Permissão não cadastrada, não encontrada");
        
        permissao.setUltimaAtualizacao(new java.sql.Date(System.currentTimeMillis()));

        _PermissaoRepository.save(permissao);
    }

    public void ExcluirPermissao(UUID id){
        if(!_PermissaoRepository.existsById(id))
            throw new RuntimeException("Permissão não cadastrada, não encontrada");

        var permissao = _PermissaoRepository.findById(id).get();

        permissao.setAtivo(false);

        _PermissaoRepository.save(permissao);
    }

    public List<Permissao> ListarPermissoes(){
        return _PermissaoRepository.findAll();
    }
}
