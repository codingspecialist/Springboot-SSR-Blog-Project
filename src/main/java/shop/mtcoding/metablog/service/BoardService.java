package shop.mtcoding.metablog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.metablog.core.annotation.MyLog;
import shop.mtcoding.metablog.core.exception.ssr.Exception400;
import shop.mtcoding.metablog.core.exception.ssr.Exception500;
import shop.mtcoding.metablog.core.util.MyParseUtil;
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
            String thumbnail = MyParseUtil.getThumbnail(saveInDTO.getContent());
            boardRepository.save(saveInDTO.toEntity(user, thumbnail));
        }catch (Exception e){
            throw new Exception500("글쓰기 실패 : "+e.getMessage());
        }
    }

    @MyLog
    public Page<Board> 게시글목록보기V1(PageRequest pageRequest) {
        Page<Board> boardPG = boardRepository.findAll(pageRequest);
        // 1. API 요청이면 BoardResponse.BoardListOutDTO 가 필요함
        // 2. API 요청이 아니기 때문에 Entity를 응답할 수 있음.
        // 3. OSIV가 false이기 때문에 Lazy Loading을 Service 단에서 완료해야 함.
        // 4. 아래와 같이 컬렉션의 연관된 엔티티 하나를 Lazy Loading 하게 되면 1+N 문제 발생
        // 5. @ManyToOne을 Lazy 전략으로 가져가면 default_batch_fetch_size 가 발동하지 않는다.
        // 6. @ManyToOne을 Lazy 전략으로 가져갈 때 findAll로 컬렉션을 조회하려면 join fetch를 해주자.
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

    @MyLog
    public Board 게시글상세보기(Long id) {
        Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
                ()-> new Exception400("id", "아이디를 찾을 수 없습니다")
        );
        // 1. Lazy Loading 하는 것 보다 join fetch 하는 것이 좋다.
        // 2. 왜 Lazy를 쓰냐면, 쓸데 없는 조인 쿼리를 줄이기 위해서이다.
        // 3. 사실 @ManyToOne은 Eager 전략을 쓰는 것이 좋다.
        // boardPS.getUser().getUsername();
        return boardPS;
    }
}
