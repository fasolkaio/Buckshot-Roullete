package sk.tuke.gamestudio.service.JPA;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentException;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

public class CommentServiceJPA implements CommentService {
    @Override
    public void addComment(Comment comment) throws CommentException {

    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return List.of();
    }

    @Override
    public void reset() throws CommentException {

    }
}
