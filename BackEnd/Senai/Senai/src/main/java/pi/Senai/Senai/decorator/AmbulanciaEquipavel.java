package pi.Senai.Senai.decorator;

import java.util.ArrayList;
import java.util.List;

import pi.Senai.Senai.entity.Ambulancia;

public class AmbulanciaEquipavel implements RecursoDeEmergencia {

    private final Ambulancia ambulancia;

    public AmbulanciaEquipavel(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public String getDescricao() {
        return ambulancia.getDescricao();
    }

    @Override
    public double getPesoKg() {
        return ambulancia.getPesoBaseKg();
    }

    @Override
    public List<String> getItensEquipados() {
        return new ArrayList<>();
    }
}
