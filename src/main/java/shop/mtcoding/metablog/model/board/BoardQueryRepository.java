package shop.mtcoding.metablog.model.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {
    private final EntityManager em;

    public Page<Board> findAll(Pageable pageable) {
        List<Board> boardListPS = em.createQuery("select b from Board b join fetch b.user")
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(boardListPS, pageable, boardListPS.size());
    }
}
