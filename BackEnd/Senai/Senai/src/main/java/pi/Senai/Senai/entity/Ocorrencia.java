package pi.Senai.Senai.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pi.Senai.Senai.enums.GravidadeOcorrencia;
import pi.Senai.Senai.enums.StatusOcorrencia;

@Entity
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @Column(nullable = false, unique = true, length = 20)
    private String Protocolo;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String Titulo;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String Descricao;

    @NotBlank
    @Column(nullable = false)
    private String Tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GravidadeOcorrencia Gravidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOcorrencia Status;

    @NotBlank
    @Column(nullable = false)
    private String Endereco;

    // EXCLUIR 
    @Column
    private Double Latitude;

    // EXCLUIR 
    @Column
    private Double Longitude;

    @Column(nullable = false)
    private LocalDateTime DataHoraAbertura;

    @Column
    private LocalDateTime DataHoraEncerramento;

    @NotNull
    @Column(nullable = false)
    private UUID OperadorId;

    @Column
    private UUID EquipeId;

    @Column
    private UUID VeiculoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ambulancia_id")
    private Ambulancia ambulancia;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getProtocolo() {
        return Protocolo;
    }

    public void setProtocolo(String protocolo) {
        Protocolo = protocolo;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public GravidadeOcorrencia getGravidade() {
        return Gravidade;
    }

    public void setGravidade(GravidadeOcorrencia gravidade) {
        Gravidade = gravidade;
    }

    public StatusOcorrencia getStatus() {
        return Status;
    }

    public void setStatus(StatusOcorrencia status) {
        Status = status;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public LocalDateTime getDataHoraAbertura() {
        return DataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        DataHoraAbertura = dataHoraAbertura;
    }

    public LocalDateTime getDataHoraEncerramento() {
        return DataHoraEncerramento;
    }

    public void setDataHoraEncerramento(LocalDateTime dataHoraEncerramento) {
        DataHoraEncerramento = dataHoraEncerramento;
    }

    public UUID getOperadorId() {
        return OperadorId;
    }

    public void setOperadorId(UUID operadorId) {
        OperadorId = operadorId;
    }

    public UUID getEquipeId() {
        return EquipeId;
    }

    public void setEquipeId(UUID equipeId) {
        EquipeId = equipeId;
    }

    public UUID getVeiculoId() {
        return VeiculoId;
    }

    public void setVeiculoId(UUID veiculoId) {
        VeiculoId = veiculoId;
    }

    public Ambulancia getAmbulancia() {
        return ambulancia;
    }

    public void setAmbulancia(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }
}