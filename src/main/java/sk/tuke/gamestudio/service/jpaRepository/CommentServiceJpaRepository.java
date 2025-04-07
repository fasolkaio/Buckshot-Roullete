package sk.tuke.gamestudio.service.jpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.repository.CommentRepository;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@Transactional
public class CommentServiceJpaRepository implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void addComment(Comment comment) throws CommentException {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return commentRepository.findCommentsByGameOrderByCommentedOnDesc(game);
    }

    @Override
    public void reset() throws CommentException {
        commentRepository.reset();
    }
}
