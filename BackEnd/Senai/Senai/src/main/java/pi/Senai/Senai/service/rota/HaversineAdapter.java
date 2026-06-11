package pi.Senai.Senai.service.rota;

import org.springframework.stereotype.Component;

// PATTERN: Adapter
@Component
public class HaversineAdapter implements CalculadorDeDistancia {

    private final ServicoDeGeolocalizacao servico = new HaversineCalculador();

    @Override
    public double calcular(double latOrigem, double lonOrigem, double latDestino, double lonDestino) {
        Coordenada origem = new Coordenada(latOrigem, lonOrigem);
        Coordenada destino = new Coordenada(latDestino, lonDestino);
        return servico.calcularDistanciaEntrePontos(origem, destino);
    }
}
