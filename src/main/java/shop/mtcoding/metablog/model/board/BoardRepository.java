package shop.mtcoding.metablog.model.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //@EntityGraph(attributePaths = "user")
    @Override
    Page<Board> findAll(Pageable pageable);
}
