package shop.mtcoding.metablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.core.exception.ssr.Exception500;
import shop.mtcoding.metablog.dto.board.BoardRequest;
import shop.mtcoding.metablog.model.board.BoardRepository;
import shop.mtcoding.metablog.model.user.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @MyLog
    @Transactional
    public void 글쓰기(BoardRequest.SaveInDTO saveInDTO, User user) {
        try {
            boardRepository.save(saveInDTO.toEntity(user));
        }catch (Exception e){
            throw new Exception500("글쓰기 실패 : "+e.getClass());
        }
    }
}
