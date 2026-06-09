package pi.Senai.Senai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pi.Senai.Senai.Entity.Usuario;
import pi.Senai.Senai.enums.NivelAcesso;
import pi.Senai.Senai.repository.UsuarioRepository;

@Component
public class UsuarioInicial implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 


    @Override
    public void run(String... args) throws Exception {
        String cpfAdmin = "00000000000";

        if (usuarioRepository.findByCpf(cpfAdmin).isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setCpf(cpfAdmin);
            admin.setEmail("admin@email.com");
            
            admin.setSenha(passwordEncoder.encode("admin123")); 
            
            admin.setNivelAcesso(NivelAcesso.ADMIN);
            admin.setAtivo(true);

            usuarioRepository.save(admin);
            System.out.println("🚀 Usuário inicial criado com sucesso (Senha com Hash)!");
        }
    }
}
