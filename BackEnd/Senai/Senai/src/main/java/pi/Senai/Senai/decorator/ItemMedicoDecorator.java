package pi.Senai.Senai.decorator;

import java.util.List;

public abstract class ItemMedicoDecorator implements RecursoDeEmergencia {

    protected final RecursoDeEmergencia recurso;

    public ItemMedicoDecorator(RecursoDeEmergencia recurso) {
        this.recurso = recurso;
    }

    @Override
    public String getDescricao() {
        return recurso.getDescricao();
    }

    @Override
    public double getPesoKg() {
        return recurso.getPesoKg();
    }

    @Override
    public List<String> getItensEquipados() {
        return recurso.getItensEquipados();
    }
}
