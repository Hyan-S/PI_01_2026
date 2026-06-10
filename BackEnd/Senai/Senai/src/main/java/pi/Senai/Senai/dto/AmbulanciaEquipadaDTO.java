package pi.Senai.Senai.dto;

import java.util.List;

public class AmbulanciaEquipadaDTO {

    private String descricao;
    private double pesoTotalKg;
    private List<String> itensEquipados;

    public AmbulanciaEquipadaDTO(String descricao, double pesoTotalKg, List<String> itensEquipados) {
        this.descricao = descricao;
        this.pesoTotalKg = pesoTotalKg;
        this.itensEquipados = itensEquipados;
    }

    public String getDescricao() { return descricao; }
    public double getPesoTotalKg() { return pesoTotalKg; }
    public List<String> getItensEquipados() { return itensEquipados; }
}
