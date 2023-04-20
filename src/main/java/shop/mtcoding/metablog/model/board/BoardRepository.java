package shop.mtcoding.metablog.model.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //@EntityGraph(attributePaths = "user")
    Page<Board> findAll(Pageable pageable);

    @Query("select b from Board b join fetch b.user where b.id = :id")
    Optional<Board> findById(@Param("id") Long id);
}
