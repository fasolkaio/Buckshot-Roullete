package sk.tuke.kpi.kp.ak.gamestudio.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Data
@Setter
@ToString
public class Score {
    String playerName;
    int score;
    Date playingDate;
}
