package shop.mtcoding.metablog.model.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.mtcoding.metablog.model.love.Love;

public interface ReplyRepository extends JpaRepository<Reply, Love> {
}
