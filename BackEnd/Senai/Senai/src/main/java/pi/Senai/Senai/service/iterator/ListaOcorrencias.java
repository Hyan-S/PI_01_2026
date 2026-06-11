package pi.Senai.Senai.service.iterator;

import java.util.Iterator;
import java.util.List;

import pi.Senai.Senai.entity.Ocorrencia;

public class ListaOcorrencias implements Iterable<Ocorrencia> {

    private final List<Ocorrencia> ocorrencias;

    public ListaOcorrencias(List<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    @Override
    public Iterator<Ocorrencia> iterator() {
        return new OcorrenciaIteradorPorPrioridade(ocorrencias);
    }
}
