package pi.Senai.Senai.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pi.Senai.Senai.infra.SecurityFilter;

@Configuration
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    // Injeção do filtro personalizado via construtor
    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }
  
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilitado pois usamos JWT
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Ativa a configuração de CORS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sem estado no servidor
            .authorizeHttpRequests(auth -> auth
                // Rota de autenticação pública
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                
                
                // O método hasRole ou hasAuthority valida a ROLE_ADMIN / ROLE_0 que o filtro injeta
                .requestMatchers("/api/usuarios/cadastrar").hasAuthority("ROLE_ADMIN") 
                
                // Qualquer outra rota do sistema exige autenticação por token
                .anyRequest().authenticated()
            )
            // Executa o filtro JWT antes do filtro padrão de autenticação do Spring
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuração de CORS para o Angular não ser bloqueado pelo navegador
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // URL do projeto Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}