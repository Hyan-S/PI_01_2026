package pi.Senai.Senai.dto;

import java.util.UUID;

public class DespachoDTO {
    private UUID veiculoId;
    private UUID equipeId;

    public UUID getVeiculoId() { return veiculoId; }
    public void setVeiculoId(UUID veiculoId) { this.veiculoId = veiculoId; }

    public UUID getEquipeId() { return equipeId; }
    public void setEquipeId(UUID equipeId) { this.equipeId = equipeId; }
}
