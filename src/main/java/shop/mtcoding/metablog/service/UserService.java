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

    @MyLog
    @Transactional
    public void 회원가입(UserRequest.JoinInDTO joinInDTO) throws Exception500 {
        joinInDTO.setPassword(passwordEncoder.encode(joinInDTO.getPassword()));
        userRepository.save(joinInDTO.toEntity());
    }
}
