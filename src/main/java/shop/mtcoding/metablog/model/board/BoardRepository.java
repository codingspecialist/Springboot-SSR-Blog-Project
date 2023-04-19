package shop.mtcoding.metablog.model.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Long, Board> {
}
