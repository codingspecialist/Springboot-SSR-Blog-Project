package shop.mtcoding.metablog;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.metablog.model.user.User;
import shop.mtcoding.metablog.model.user.UserRepository;

@SpringBootApplication
public class MetablogApplication {

    @Bean
    CommandLineRunner init(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        return args -> {
            User ssar = User.builder()
                    .username("ssar")
                    .password(passwordEncoder.encode("1234"))
                    .email("ssar@nate.com")
                    .role("USER")
                    .build();
            userRepository.save(ssar);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MetablogApplication.class, args);
    }

}
