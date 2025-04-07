package sk.tuke.gamestudio.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment save(Comment comment);

    List<Comment> findCommentsByGameOrderByCommentedOnDesc(String game);

    @Modifying
    @Query(value = "DELETE FROM comment", nativeQuery = true)
    void reset();
}
