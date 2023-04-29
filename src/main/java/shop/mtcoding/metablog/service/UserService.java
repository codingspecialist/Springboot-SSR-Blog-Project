package shop.mtcoding.metablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.core.exception.csr.ExceptionApi400;
import shop.mtcoding.metablog.core.exception.ssr.Exception400;
import shop.mtcoding.metablog.core.exception.ssr.Exception401;
import shop.mtcoding.metablog.core.exception.ssr.Exception404;
import shop.mtcoding.metablog.core.exception.ssr.Exception500;
import shop.mtcoding.metablog.core.util.MyFileUtil;
import shop.mtcoding.metablog.dto.ResponseDTO;
import shop.mtcoding.metablog.dto.user.UserRequest;
import shop.mtcoding.metablog.dto.user.UserResponse;
import shop.mtcoding.metablog.model.user.User;
import shop.mtcoding.metablog.model.user.UserRepository;

import java.util.Optional;

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
            throw new Exception500("회원가입 실패 : "+e.getMessage());
        }

    }

    @MyLog
    public void 유저네임중복체크(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);
        if(userOP.isPresent()){
            throw new ExceptionApi400("username", "유저네임이 중복되었어요");
        }
    }

    @MyLog
    @Transactional
    public User 프로필사진수정(MultipartFile profile, Long id) {
        String uuidImageName = MyFileUtil.write(uploadFolder, profile);
        User userPS = userRepository.findById(id)
                .orElseThrow(()->new Exception500("로그인 된 유저가 DB에 존재하지 않음"));
        try {
            userPS.changeProfile(uuidImageName);
            return userPS;
        }catch (Exception e){
            throw new Exception500("프로필 사진 등록 실패 : "+e.getMessage());
        }
    } // 더티체킹 업데이트

    @MyLog
    public User 회원프로필보기(Long id) {
        User userPS = userRepository.findById(id)
                .orElseThrow(()->new Exception400("id", "해당 유저가 존재하지 않습니다"));
        return userPS;
    }

    // 내부로직이 동일한 서비스가 있어도 그냥 하나 더 만들자!! - 머리를 쓰지 않는 전략
    // 나중에 화면에 대해서 서비스 로직이 변경되면 더 골치 아프다. 결국 쪼개져야 한다.
    @MyLog
    public User 회원정보보기(Long id) {
        User userPS = userRepository.findById(id)
                .orElseThrow(()->new Exception400("id", "해당 유저가 존재하지 않습니다"));
        return userPS;
    }

    @MyLog
    @Transactional
    public User 회원정보수정(Long id, UserRequest.UpdateInDTO updateInDTO) {
        User userPS = userRepository.findById(id)
                .orElseThrow(()->new Exception400("id", "해당 유저가 존재하지 않습니다"));
        updateInDTO.setPassword(passwordEncoder.encode(updateInDTO.getPassword()));
        userPS.update(updateInDTO.getPassword(), updateInDTO.getEmail());
        return userPS;
    } // 더티체킹
}
