package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import java.util.List;

public class CommentServiceJDBC implements CommentService {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS comment (game VARCHAR(64) NOT NULL, player VARCHAR(64) NOT NULL, points INTEGER NOT NULL, playedOn TIMESTAMP NOT NULL);";

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
