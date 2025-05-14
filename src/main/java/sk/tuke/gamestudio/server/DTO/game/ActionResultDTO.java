package sk.tuke.gamestudio.server.DTO.game;

import lombok.Getter;

@Getter
public abstract class ActionResultDTO {
    private final ActionType type;

    protected ActionResultDTO(ActionType actionType) {
        this.type = actionType;
    }
}
