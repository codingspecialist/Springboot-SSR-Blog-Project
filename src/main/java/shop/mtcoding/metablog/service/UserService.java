package shop.mtcoding.metablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.core.exception.ssr.Exception500;
import shop.mtcoding.metablog.dto.user.UserRequest;
import shop.mtcoding.metablog.model.user.UserRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    // INSERT, UPDATE, DELETE 는 try catch 필수
    @MyLog
    @Transactional
    public void 회원가입(UserRequest.JoinInDTO joinInDTO){
        // try catch를 걸어야 repository 에서 터지는 에러를 잡을 수 있고, 잡으면 RuntimeException으로 던져야 handler가 처리할 수 있다.
        try {
            joinInDTO.setPassword(passwordEncoder.encode(joinInDTO.getPassword()));
            userRepository.save(joinInDTO.toEntity());
        }catch (Exception e){
            throw new Exception500("회원가입 실패 : "+e.getClass());
        }

    }
}
