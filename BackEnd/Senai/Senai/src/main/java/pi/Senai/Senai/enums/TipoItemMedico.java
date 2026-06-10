package pi.Senai.Senai.enums;

public enum TipoItemMedico {

    DESFIBRILADOR("Desfibrilador", 5.0),
    KIT_PRIMEIROS_AUXILIOS("Kit de Primeiros Auxílios", 3.0),
    MACA("Maca", 15.0),
    OXIGENIO("Cilindro de Oxigênio", 8.0),
    RESPIRADOR("Respirador", 4.0);

    private final String descricao;
    private final double pesoKg;

    TipoItemMedico(String descricao, double pesoKg) {
        this.descricao = descricao;
        this.pesoKg = pesoKg;
    }

    public String getDescricao() { return descricao; }
    public double getPesoKg() { return pesoKg; }
}
