package pi.Senai.Senai.service.relatorio;

import java.util.List;

public class RelatorioImpl implements Relatorio {

    private final String titulo;
    private final List<Object> dados;

    public RelatorioImpl(String titulo, List<Object> dados) {
        this.titulo = titulo;
        this.dados = dados;
    }

    @Override
    public String getTitulo() { return titulo; }

    @Override
    public List<Object> getDados() { return dados; }
}
