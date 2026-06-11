package pi.Senai.Senai.service.relatorio;

// PATTERN: Factory Method
public abstract class RelatorioFactory {

    protected abstract Relatorio criarRelatorio();

    public Relatorio gerarERetornar() {
        return criarRelatorio();
    }
}
