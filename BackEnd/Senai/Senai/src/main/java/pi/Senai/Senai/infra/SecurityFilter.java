package pi.Senai.Senai.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pi.Senai.Senai.Entity.Usuario;
import pi.Senai.Senai.Service.TokenService;
import pi.Senai.Senai.dto.UsuarioResponseDTO;
import pi.Senai.Senai.repository.UsuarioRepository;

import java.io.IOException;
import java.security.Security;
import java.util.Collection;
import java.util.Collections;

public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String token=recuperarToken(request);
        if (token != null){
            String login =tokenService.validarToken(token);
            if (!login.isEmpty()){
                Usuario usuario= usuarioRepository.findByCpf(login).orElseGet(()->usuarioRepository.findByEmail(login).orElse(null));

                if (usuario != null && usuario.isAtivo()){
                    SimpleGrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+usuario.getNivelAcesso().name());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,null, Collections.singleton(authority));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request){
        String authHeader=request.getHeader("Authorization");
        if (authHeader == null|| !authHeader.startsWith("Bearer ")){
            return null;
        }
        return authHeader.replace("Bearer ","");
    }
}
