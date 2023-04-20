package shop.mtcoding.metablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.core.exception.ssr.Exception500;
import shop.mtcoding.metablog.dto.board.BoardRequest;
import shop.mtcoding.metablog.model.board.Board;
import shop.mtcoding.metablog.model.board.BoardQueryRepository;
import shop.mtcoding.metablog.model.board.BoardRepository;
import shop.mtcoding.metablog.model.user.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

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

    @MyLog
    public Page<Board> 게시글목록보기V1(PageRequest pageRequest) {
        Page<Board> boardPG = boardRepository.findAll(pageRequest);
        // 1. API 요청이면 BoardResponse.BoardListOutDTO 가 필요함
        // 2. API 요청이 아니기 때문에 Entity를 응답할 수 있음.
        // 3. OSIV가 false이기 때문에 Lazy Loading을 Service 단에서 완료해야 함.
        // 4. 아래와 같이 Lazy Loading 하게 되면 1+N 문제 발생
        boardPG.getContent().stream().forEach(board -> {
            board.getUser().getUsername();
        });
        return boardPG;
    }

    @MyLog
    public Page<Board> 게시글목록보기V2(PageRequest pageRequest) {
        // FetchJoin
        Page<Board> boardPG = boardQueryRepository.findAll(pageRequest);
        return boardPG;
    }
}
