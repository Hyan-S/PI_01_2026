package pi.Senai.Senai.entity.dto;

import jakarta.validation.constraints.NotBlank;

public class AmbulanciaRequestDTO {

    @NotBlank
    private String descricao;

    @NotBlank
    private String placa;

    private String observacoes;

    public AmbulanciaRequestDTO() {
    }

    public AmbulanciaRequestDTO(String descricao, String placa, String observacoes) {
        this.descricao = descricao;
        this.placa = placa;
        this.observacoes = observacoes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}