package shop.mtcoding.metablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.core.exception.ssr.Exception400;
import shop.mtcoding.metablog.core.exception.ssr.Exception403;
import shop.mtcoding.metablog.core.exception.ssr.Exception500;
import shop.mtcoding.metablog.core.util.MyParseUtil;
import shop.mtcoding.metablog.dto.board.BoardRequest;
import shop.mtcoding.metablog.model.board.Board;
import shop.mtcoding.metablog.model.board.BoardJPQLRepository;
import shop.mtcoding.metablog.model.board.BoardRepository;
import shop.mtcoding.metablog.model.user.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardJPQLRepository boardQueryRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @MyLog
    @Transactional
    public void 글쓰기(BoardRequest.SaveInDTO saveInDTO, User user) {
        try {
            String thumbnail = MyParseUtil.getThumbnail(saveInDTO.getContent());
            boardRepository.save(saveInDTO.toEntity(user, thumbnail));
        }catch (Exception e){
            throw new Exception500("글쓰기 실패 : "+e.getMessage());
        }
    }

    /*
        isEmpty() 메소드는 문자열의 길이가 0인지 검사합니다.
        isBlank() 메소드는 문자열이 비어있거나 공백 문자열(whitespace-only string)인지 검사합니다.
    */
    @MyLog
    public Page<Board> 게시글목록보기(int page, String keyword) {
        if(keyword.isBlank()){
            Page<Board> boardPG = boardQueryRepository.findAll(page);
            return boardPG;
        }else{
            Page<Board> boardPG = boardQueryRepository.findAllByKeyword(page, keyword);
            return boardPG;
        }

    }

    @MyLog
    public Board 게시글상세보기(Long id) {
        Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
                ()-> new Exception400("id", "게시글 아이디를 찾을 수 없습니다")
        );
        // 1. Lazy Loading 하는 것 보다 join fetch 하는 것이 좋다.
        // 2. 왜 Lazy를 쓰냐면, 쓸데 없는 조인 쿼리를 줄이기 위해서이다.
        // 3. 사실 @ManyToOne은 Eager 전략을 쓰는 것이 좋다.
        // boardPS.getUser().getUsername();
        return boardPS;
    }

    @MyLog
    public Board 게시글수정상세보기(Long id, Long userId) {
        Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
                ()-> new Exception400("id", "게시글 아이디를 찾을 수 없습니다")
        );
        if(boardPS.getUser().getId().longValue() != userId){
            throw new Exception403("권한이 없습니다");
        }
        return boardPS;
    }

    @MyLog
    @Transactional
    public void 게시글삭제(Long id, Long userId) {
        Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
                ()-> new Exception400("id", "게시글 아이디를 찾을 수 없습니다")
        );
        if(boardPS.getUser().getId() != userId){
            throw new Exception403("권한이 없습니다");
        }
        try {
            boardRepository.delete(boardPS);
        }catch (Exception e){
            throw new Exception500("게시글 삭제 실패 : "+e.getMessage());
        }
    }
}
