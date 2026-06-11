package pi.Senai.Senai.decorator.itens;

import java.util.ArrayList;
import java.util.List;

import pi.Senai.Senai.decorator.ItemMedicoDecorator;
import pi.Senai.Senai.decorator.RecursoDeEmergencia;
import pi.Senai.Senai.enums.TipoItemMedico;

public class KitPrimeirosAuxiliosDecorator extends ItemMedicoDecorator {

    public KitPrimeirosAuxiliosDecorator(RecursoDeEmergencia recurso) {
        super(recurso);
    }

    @Override
    public String getDescricao() {
        return recurso.getDescricao() + " + " + TipoItemMedico.KIT_PRIMEIROS_AUXILIOS.getDescricao();
    }

    @Override
    public List<String> getItensEquipados() {
        List<String> itens = new ArrayList<>(recurso.getItensEquipados());
        itens.add(TipoItemMedico.KIT_PRIMEIROS_AUXILIOS.name());
        return itens;
    }
}
