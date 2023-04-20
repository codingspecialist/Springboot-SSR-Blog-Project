package shop.mtcoding.metablog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.metablog.core.auth.MyUserDetails;
import shop.mtcoding.metablog.dto.board.BoardRequest;
import shop.mtcoding.metablog.service.BoardService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping({"/", "/board"})
    public String main(){
        return "board/main";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board/save")
    public @ResponseBody String save(
            @Valid BoardRequest.SaveInDTO saveInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
            ) {
        boardService.글쓰기(saveInDTO, myUserDetails.getUser());

        return "redirect:/";
    }

}
