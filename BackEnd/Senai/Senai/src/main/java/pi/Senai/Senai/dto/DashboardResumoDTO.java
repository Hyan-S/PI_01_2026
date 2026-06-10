package pi.Senai.Senai.dto;

public class DashboardResumoDTO {

    private long ocorrenciasAtivas;
    private long ambulanciasDisponiveis;
    private long equipesEmCampo;

    public DashboardResumoDTO(long ocorrenciasAtivas, long ambulanciasDisponiveis, long equipesEmCampo) {
        this.ocorrenciasAtivas = ocorrenciasAtivas;
        this.ambulanciasDisponiveis = ambulanciasDisponiveis;
        this.equipesEmCampo = equipesEmCampo;
    }

    public long getOcorrenciasAtivas() { return ocorrenciasAtivas; }
    public long getAmbulanciasDisponiveis() { return ambulanciasDisponiveis; }
    public long getEquipesEmCampo() { return equipesEmCampo; }
}
