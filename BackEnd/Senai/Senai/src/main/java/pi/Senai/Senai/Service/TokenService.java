package pi.Senai.Senai.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pi.Senai.Senai.Entity.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret:fallback-secret-chave-longa-e-segura}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            Algorithm algorithm=Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("pi-senai-api").withSubject(usuario.getCpf()).withClaim("role",usuario.getNivelAcesso().name()).withExpiresAt(gerarDataExpiracao()).sign(algorithm);
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String token){
        try {
            Algorithm algorithm= Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("pi-senai-api").build().verify(token).getSubject();
        }catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant gerarDataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
