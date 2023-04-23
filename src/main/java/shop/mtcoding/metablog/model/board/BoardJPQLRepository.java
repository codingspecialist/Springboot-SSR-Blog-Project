package shop.mtcoding.metablog.model.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardJPQLRepository {
    private final EntityManager em;

    public Page<Board> findAll(int page) {
        int size = 8;
        int startPosition = page*size;
        List<Board> boardListPS = em.createQuery("select b from Board b join fetch b.user order by b.id desc")
                .setFirstResult(startPosition) // startPosition
                .setMaxResults(size) // size
                .getResultList();
        Long totalCount = em.createQuery("select count(b) from Board b", Long.class).getSingleResult();
        return new PageImpl<>(boardListPS, PageRequest.of(page, size), totalCount);
    }
}
