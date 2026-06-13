package pi.Senai.Senai.dto;

import java.util.UUID;

public class RetiradaEstoqueDTO {
    private Double quantidade;
    private UUID idAmbulancia;

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public UUID getIdAmbulancia() {
        return idAmbulancia;
    }

    public void setIdAmbulancia(UUID idAmbulancia) {
        this.idAmbulancia = idAmbulancia;
    }
}