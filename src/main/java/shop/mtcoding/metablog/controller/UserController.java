package shop.mtcoding.metablog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.dto.user.UserRequest;
import shop.mtcoding.metablog.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinInDTO joinInDTO, Errors errors){
        userService.회원가입(joinInDTO);
        return "redirect:/loginForm";
    }
}