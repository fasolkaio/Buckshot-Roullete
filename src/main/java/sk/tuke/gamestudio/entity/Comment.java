package sk.tuke.gamestudio.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
    private String game;
    private String player;
    private String comment;
    private Date commentedOn;

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", comment=" + comment +
                ", commentedOn=" + commentedOn +
                '}';
    }
}
