package pi.Senai.Senai.service.rota;

public class HaversineCalculador implements ServicoDeGeolocalizacao {

    private static final double RAIO_TERRA_KM = 6371.0;

    @Override
    public double calcularDistanciaEntrePontos(Coordenada pontoA, Coordenada pontoB) {
        double dLat = Math.toRadians(pontoB.getLatitude() - pontoA.getLatitude());
        double dLon = Math.toRadians(pontoB.getLongitude() - pontoA.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(pontoA.getLatitude())) * Math.cos(Math.toRadians(pontoB.getLatitude()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return RAIO_TERRA_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
