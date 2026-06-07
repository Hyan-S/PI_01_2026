package pi.Senai.Senai.entity.dto;

import pi.Senai.Senai.entity.Ambulancia;

import java.util.UUID;

public class AmbulanciaResponseDTO {

    private UUID id;
    private String descricao;
    private String placa;
    private String observacoes;
    private boolean ativo;

    public AmbulanciaResponseDTO() {
    }

    public AmbulanciaResponseDTO(UUID id, String descricao, String placa, String observacoes, boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.placa = placa;
        this.observacoes = observacoes;
        this.ativo = ativo;
    }

    public static AmbulanciaResponseDTO fromEntity(Ambulancia ambulancia) {
        return new AmbulanciaResponseDTO(
                ambulancia.getId(),
                ambulancia.getDescricao(),
                ambulancia.getPlaca(),
                ambulancia.getObservacoes(),
                ambulancia.isAtivo()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}