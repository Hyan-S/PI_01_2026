package pi.Senai.Senai.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pi.Senai.Senai.dto.LoginRequestDTO;
import pi.Senai.Senai.dto.TokenResponseDTO;
import pi.Senai.Senai.service.AutenticarService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AutenticarService autenticarService;

    public AuthController(AutenticarService autenticarService) {
        this.autenticarService = autenticarService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        TokenResponseDTO token = autenticarService.autenticar(dto);
        return ResponseEntity.ok(token);
    }
}