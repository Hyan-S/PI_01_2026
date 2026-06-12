package pi.Senai.Senai.decorator.itens;

import java.util.ArrayList;
import java.util.List;

import pi.Senai.Senai.decorator.ItemMedicoDecorator;
import pi.Senai.Senai.decorator.RecursoDeEmergencia;
import pi.Senai.Senai.enums.TipoItemMedico;

public class MacaDecorator extends ItemMedicoDecorator {

    public MacaDecorator(RecursoDeEmergencia recurso) {
        super(recurso);
    }

    @Override
    public String getDescricao() {
        return recurso.getDescricao() + " + " + TipoItemMedico.MACA.getDescricao();
    }

    @Override
    public double getPesoKg() {
        return recurso.getPesoKg() + TipoItemMedico.MACA.getPesoKg();
    }

    @Override
    public List<String> getItensEquipados() {
        List<String> itens = new ArrayList<>(recurso.getItensEquipados());
        itens.add(TipoItemMedico.MACA.name());
        return itens;
    }
}
