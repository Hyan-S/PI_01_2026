package pi.Senai.Senai.dto;

import java.util.List;

public class AmbulanciaEquipadaDTO {

    private String descricao;
    private List<String> itensEquipados;

    public AmbulanciaEquipadaDTO(String descricao, List<String> itensEquipados) {
        this.descricao = descricao;
        this.itensEquipados = itensEquipados;
    }

    public String getDescricao() { return descricao; }
    public List<String> getItensEquipados() { return itensEquipados; }
}
