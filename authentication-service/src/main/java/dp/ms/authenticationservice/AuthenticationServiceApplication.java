package dp.ms.authenticationservice;

import dp.ms.authenticationservice.models.ApplicationUser;
import dp.ms.authenticationservice.models.Role;
import dp.ms.authenticationservice.repository.RoleRepository;
import dp.ms.authenticationservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (roleRepository.findByAuthority("USER").isEmpty()){
                roleRepository.save(new Role(0, "USER"));
            }
            if (roleRepository.findByAuthority("ADMIN").isEmpty()) {
                roleRepository.save(new Role(0, "ADMIN"));
            }
        };
    }

}
