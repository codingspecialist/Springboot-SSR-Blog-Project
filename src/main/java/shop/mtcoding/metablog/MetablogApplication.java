package shop.mtcoding.metablog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.metablog.model.board.Board;
import shop.mtcoding.metablog.model.board.BoardRepository;
import shop.mtcoding.metablog.model.user.User;
import shop.mtcoding.metablog.model.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MetablogApplication extends DummyEntity{

    @Bean
    CommandLineRunner init(UserRepository userRepository, BoardRepository boardRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            User ssar = newUser("ssar", passwordEncoder);
            User cos = newUser("cos", passwordEncoder);
            List<Board> boardList = new ArrayList<>();
            for (int i = 1; i < 16; i++) {
                boardList.add(newBoard("제목"+i, ssar));
            }
            userRepository.saveAll(Arrays.asList(ssar, cos));
            boardRepository.saveAll(boardList);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MetablogApplication.class, args);
    }

}
