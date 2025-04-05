package sk.tuke.gamestudio.service.JPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return entityManager.createQuery("select c from Comment c where c.game = :game order by c.commentedOn desc", Comment.class)
                .setParameter("game", game)
                .getResultList();
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createNativeQuery("DELETE FROM Comment").executeUpdate();
    }
}
