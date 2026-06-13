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
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    private String protocolo;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String titulo;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotBlank
    @Column(nullable = false)
    private String tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GravidadeOcorrencia gravidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOcorrencia status;

    @NotBlank
    @Column(nullable = false)
    private String endereco;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(nullable = false)
    private LocalDateTime dataHoraAbertura;

    @Column
    private LocalDateTime dataHoraEncerramento;

    @NotNull
    @Column(nullable = false)
    private UUID operadorId;

    @Column
    private UUID equipeId;

    @Column
    private UUID veiculoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ambulancia_id")
    private Ambulancia ambulancia;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public GravidadeOcorrencia getGravidade() {
        return gravidade;
    }

    public void setGravidade(GravidadeOcorrencia gravidade) {
        this.gravidade = gravidade;
    }

    public StatusOcorrencia getStatus() {
        return status;
    }

    public void setStatus(StatusOcorrencia status) {
        this.status = status;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public LocalDateTime getDataHoraEncerramento() {
        return dataHoraEncerramento;
    }

    public void setDataHoraEncerramento(LocalDateTime dataHoraEncerramento) {
        this.dataHoraEncerramento = dataHoraEncerramento;
    }

    public UUID getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(UUID operadorId) {
        this.operadorId = operadorId;
    }

    public UUID getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(UUID equipeId) {
        this.equipeId = equipeId;
    }

    public UUID getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(UUID veiculoId) {
        this.veiculoId = veiculoId;
    }

    public Ambulancia getAmbulancia() {
        return ambulancia;
    }

    public void setAmbulancia(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }
}
