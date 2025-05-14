package sk.tuke.gamestudio.server.DTO.game;

import lombok.Getter;

@Getter
public class ReloadActionResultDTO extends ActionResultDTO {
    private final int blanks;
    private final int lives;

    protected ReloadActionResultDTO(ActionType actionType, int blanks, int lives) {
        super(actionType);
        this.blanks = blanks;
        this.lives = lives;
    }
}
