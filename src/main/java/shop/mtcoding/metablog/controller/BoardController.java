package shop.mtcoding.metablog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.metablog.core.auth.MyUserDetails;
import shop.mtcoding.metablog.dto.board.BoardRequest;
import shop.mtcoding.metablog.dto.board.BoardResponse;
import shop.mtcoding.metablog.model.board.Board;
import shop.mtcoding.metablog.service.BoardService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping( "/boards/{id}")
    public String detail(@PathVariable Long id, Model model){
        Board board = boardService.게시글상세보기(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping({"/", "/boards"})
    public String main(@RequestParam(defaultValue = "0") int page, Model model){
        PageRequest pageRequest = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<Board> boardPG = boardService.게시글목록보기V2(pageRequest);
        model.addAttribute("boardPG", boardPG);
        return "board/main";
    }

    @GetMapping("/boards/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/boards/save")
    public String save(
            @Valid BoardRequest.SaveInDTO saveInDTO,
            Errors errors,
            @AuthenticationPrincipal MyUserDetails myUserDetails
            ) {
        boardService.글쓰기(saveInDTO, myUserDetails.getUser());

        return "redirect:/";
    }

}
