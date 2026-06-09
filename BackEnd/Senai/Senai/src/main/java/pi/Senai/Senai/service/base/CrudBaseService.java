package pi.Senai.Senai.service.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pi.Senai.Senai.entity.base.EntidadeGerenciavel;

// PATTERN: Template Method
public abstract class CrudBaseService<T extends EntidadeGerenciavel, ID> {

    protected abstract JpaRepository<T, ID> getRepositorio();

    protected abstract String getMensagemNaoEncontrado();

    protected abstract ID getIdDaEntidade(T entidade);

    protected void prepararParaSalvar(T entidade) {}

    protected void prepararParaAtualizar(T entidade) {}

    public final void salvar(T entidade) {
        prepararParaSalvar(entidade);
        entidade.setDataCriacao(new java.sql.Date(System.currentTimeMillis()));
        entidade.setAtivo(true);
        getRepositorio().save(entidade);
    }

    public final void atualizar(T entidade) {
        if (!getRepositorio().existsById(getIdDaEntidade(entidade)))
            throw new RuntimeException(getMensagemNaoEncontrado());
        prepararParaAtualizar(entidade);
        entidade.setUltimaAtualizacao(new java.sql.Date(System.currentTimeMillis()));
        getRepositorio().save(entidade);
    }

    public final void excluir(ID id) {
        if (!getRepositorio().existsById(id))
            throw new RuntimeException(getMensagemNaoEncontrado());
        T entidade = getRepositorio().findById(id).get();
        entidade.setAtivo(false);
        getRepositorio().save(entidade);
    }

    public final List<T> listar() {
        return getRepositorio().findAll();
    }
}
