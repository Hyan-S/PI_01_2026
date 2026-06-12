package pi.Senai.Senai.service.iterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import pi.Senai.Senai.entity.Ocorrencia;

// PATTERN: Iterator
public class OcorrenciaIteradorPorPrioridade implements Iterator<Ocorrencia> {

    private final List<Ocorrencia> ocorrencias;
    private int indice = 0;

    public OcorrenciaIteradorPorPrioridade(List<Ocorrencia> ocorrencias) {
        this.ocorrencias = ocorrencias.stream()
                .sorted(Comparator.comparingInt((Ocorrencia o) -> o.getGravidade().ordinal()).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasNext() {
        return indice < ocorrencias.size();
    }

    @Override
    public Ocorrencia next() {
        if (!hasNext()) throw new NoSuchElementException();
        return ocorrencias.get(indice++);
    }
}
