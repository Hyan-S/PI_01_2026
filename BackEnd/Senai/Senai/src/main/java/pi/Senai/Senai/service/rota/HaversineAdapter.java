package pi.Senai.Senai.service.rota;

import org.springframework.stereotype.Component;

// PATTERN: Adapter
@Component
public class HaversineAdapter implements CalculadorDeDistancia {

    private final HaversineCalculador calculador = new HaversineCalculador();

    @Override
    public double calcular(double latOrigem, double lonOrigem, double latDestino, double lonDestino) {
        return calculador.calcularDistancia(latOrigem, lonOrigem, latDestino, lonDestino);
    }
}
