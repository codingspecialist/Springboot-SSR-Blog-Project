package shop.mtcoding.metablog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shop.mtcoding.metablog.config.SecurityConfig;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.core.auth.MyUserDetails;
import shop.mtcoding.metablog.core.exception.ssr.Exception400;
import shop.mtcoding.metablog.core.exception.ssr.Exception403;
import shop.mtcoding.metablog.core.util.Script;
import shop.mtcoding.metablog.dto.user.UserRequest;
import shop.mtcoding.metablog.model.user.User;
import shop.mtcoding.metablog.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

// save, update, delete
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @PostMapping("/join")
    public @ResponseBody String join(@Valid UserRequest.JoinInDTO joinInDTO, Errors errors){
        userService.회원가입(joinInDTO);
        return Script.href("회원가입 성공", "/loginForm");
    }

    @GetMapping("/s/user/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        // 1. 권한 체크
        if(id != myUserDetails.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }
        // 2. 회원 정보 조회
        User userPS = userService.회원정보보기(id);
        model.addAttribute("user", userPS);
        return "user/updateForm";
    }

    @GetMapping("/s/user/{id}/updateProfileForm")
    public String profileUpdateForm(@PathVariable Long id, Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){
        // 1. 권한 체크
        if(id != myUserDetails.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }
        User userPS = userService.회원프로필보기(id);
        model.addAttribute("user", userPS);
        return "user/profileUpdateForm";
    }

    @PostMapping("/s/user/{id}/updateProfile")
    public String profileUpdate(
            @PathVariable Long id,
            MultipartFile profile,
            @AuthenticationPrincipal MyUserDetails myUserDetails
            ){
        // 1. 권한 체크
        if(id != myUserDetails.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }

        // 2. 사진 파일 유효성 검사
        if(profile.isEmpty()){
            throw new Exception400("profile", "사진이 전송되지 않았습니다");
        }

        // 3. 사진을 파일에 저장하고 그 경로를 DB에 저장
        User userPS = userService.프로필사진수정(profile, id);

        // 4. 세션 동기화
        myUserDetails.setUser(userPS);
        session.setAttribute("sessionUser", userPS);

        return "redirect:/";
    }

    @PostMapping("/s/user/{id}/update")
    public @ResponseBody String update(
            @PathVariable Long id,
            @Valid UserRequest.UpdateInDTO updateInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
    ){
        // 1. 권한 체크
        if(id != myUserDetails.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }

        // 2. 회원정보 수정
        User user = userService.회원정보수정(id, updateInDTO);

        // 3. 세션 동기화
        myUserDetails.setUser(user);
        session.setAttribute("sessionUser", user);

        return Script.href("회원정보 수정 성공", "/");
    }
}
