package pi.Senai.Senai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pi.Senai.Senai.service.rota.CalculadorDeDistancia;

@RestController
@RequestMapping("/rota")
public class RotaController {

    @Autowired
    private CalculadorDeDistancia calculadorDeDistancia;

    @GetMapping("/distancia")
    public double calcularDistancia(
            @RequestParam double latOrigem,
            @RequestParam double lonOrigem,
            @RequestParam double latDestino,
            @RequestParam double lonDestino) {
        return calculadorDeDistancia.calcular(latOrigem, lonOrigem, latDestino, lonDestino);
    }
}
