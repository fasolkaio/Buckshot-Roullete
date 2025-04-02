package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private CommentService service;

    @Test
    public void testAddComment() {
        service.reset();
        service.addComment(new Comment("buckshot roulette", "lina", "Cool game!", new Date()));
        List<Comment> comments = service.getComments("buckshot roulette");
        assertEquals(1, comments.size());
        Comment comment = comments.get(0);
        assertEquals("buckshot roulette", comment.getGame());
        assertEquals("lina", comment.getPlayer());
        assertEquals("Cool game!", comment.getComment());
    }

    @Test
    public void testGetComments() {
        service.reset();

        service.addComment(new Comment("buckshot roulette", "pl1", "com1", new Date()));
        service.addComment(new Comment("mines", "pl2", "com2", new Date()));
        service.addComment(new Comment("buckshot roulette", "pl3", "com3", new Date()));
        service.addComment(new Comment("buckshot roulette", "pl4", "com4", new Date()));
        service.addComment(new Comment("mines", "pl5", "com5", new Date()));
        service.addComment(new Comment("buckshot roulette", "pl6", "com6", new Date()));
        service.addComment(new Comment("buckshot roulette", "pl7", "com7", new Date()));
        service.addComment(new Comment("mines", "pl8", "com8", new Date()));

        List<Comment> comments = service.getComments("buckshot roulette");
        assertEquals(5, comments.size());

        Comment comment = comments.get(4);
        assertEquals("buckshot roulette", comment.getGame());
        assertEquals("pl1", comment.getPlayer());
        assertEquals("com1", comment.getComment());

        comment = comments.get(3);
        assertEquals("buckshot roulette", comment.getGame());
        assertEquals("pl3", comment.getPlayer());
        assertEquals("com3", comment.getComment());

        comment = comments.get(2);
        assertEquals("buckshot roulette", comment.getGame());
        assertEquals("pl4", comment.getPlayer());
        assertEquals("com4", comment.getComment());

        comment = comments.get(1);
        assertEquals("buckshot roulette", comment.getGame());
        assertEquals("pl6", comment.getPlayer());
        assertEquals("com6", comment.getComment());

        comment = comments.get(0);
        assertEquals("buckshot roulette", comment.getGame());
        assertEquals("pl7", comment.getPlayer());
        assertEquals("com7", comment.getComment());
    }

    @Test
    public void testReset() {
        service.reset();
        List<Comment> comments = service.getComments("buckshot roulette");
        assertEquals(0, comments.size());
    }
}