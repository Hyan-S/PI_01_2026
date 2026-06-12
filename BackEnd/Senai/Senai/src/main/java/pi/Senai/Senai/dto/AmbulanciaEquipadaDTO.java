package pi.Senai.Senai.dto;

import java.util.List;

public class AmbulanciaEquipadaDTO {

    private String descricao;
    private List<String> itensEquipados;

    // Apenas um construtor recebendo os dois atributos
    public AmbulanciaEquipadaDTO(String descricao, List<String> itensEquipados) {
        this.descricao = descricao;
        this.itensEquipados = itensEquipados;
    }

    // Getters
    public String getDescricao() { 
        return descricao; 
    }

    public List<String> getItensEquipados() { 
        return itensEquipados; 
    }
}